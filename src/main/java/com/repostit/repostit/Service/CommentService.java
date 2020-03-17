package com.repostit.repostit.Service;

import com.repostit.repostit.Dao.Comment;
import com.repostit.repostit.Dao.NotificationEmail;
import com.repostit.repostit.Dao.Post;
import com.repostit.repostit.Dao.User;
import com.repostit.repostit.Dto.CommentDto;
import com.repostit.repostit.Exceptions.PostNotFoundException;
import com.repostit.repostit.Repository.CommentRepository;
import com.repostit.repostit.Repository.PostRepository;
import com.repostit.repostit.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static com.github.marlonlom.utilities.timeago.TimeAgo.using;
import static com.repostit.repostit.Utilities.Constants.POST_NOT_FOUND_FOR_ID;
import static com.repostit.repostit.Utilities.Constants.POST_URL;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND_FOR_ID + postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    @Transactional
    public void createComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND_FOR_ID));
        commentRepository.save(mapToComment(commentDto, post));
        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post."
                + POST_URL + post.getPostId());
        sendCommentNotification(message, post.getUser());
    }

    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder().id(comment.getId())
                .text(comment.getText())
                .duration(using(comment.getCreatedDate().toEpochMilli()))
                .username(comment.getUser().getUsername())
                .build();
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    private Comment mapToComment(CommentDto commentsDto, Post post) {
        return Comment.builder()
                .text(commentsDto.getText())
                .createdDate(Instant.now())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }

    public List<CommentDto> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }
}
