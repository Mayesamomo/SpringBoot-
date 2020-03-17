package com.repostit.repostit.Service;

import com.repostit.repostit.Dao.*;
import com.repostit.repostit.Dto.PostRequest;
import com.repostit.repostit.Dto.PostResponse;
import com.repostit.repostit.Exceptions.CommunityNotFoundException;
import com.repostit.repostit.Exceptions.PostNotFoundException;
import com.repostit.repostit.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.marlonlom.utilities.timeago.TimeAgo.using;
import static com.repostit.repostit.Dao.VoteType.UPVOTE;
import static com.repostit.repostit.Utilities.Constants.*;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND_FOR_ID + id));
        return mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    @Transactional
    public void save(@Valid PostRequest postRequest) {
        postRepository.save(mapToPost(postRequest));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCommunity(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityNotFoundException(COMMUNITY_NOT_FOUND_WITH_ID+ communityId));
        List<Post> posts = postRepository.findAllByCommunity(community);
        return posts.stream().map(this::mapToDto).collect(toList());
    }

    //TODO: Replace mapToDto with PostMapper
    PostResponse mapToDto(Post post) {
        PostResponse postResponse = PostResponse.builder()
                .id(post.getPostId())
                .postName(post.getPostName())
                .description(post.getDescription())
                .url(post.getUrl())
                .username(post.getUser().getUsername())
                .communityName(post.getCommunity().getName())
                .votesNum(post.getVoteCount())
                .commentNum(commentRepository.findByPost(post).size())
                .duration(using(post.getCreatedDate().toEpochMilli()))
                .build();
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
            if (voteForPostByUser.isPresent()) {
                VoteType voteType = voteForPostByUser.get().getVoteType();
                if (voteType.equals(UPVOTE))
                    postResponse.setUpVote(true);
                else
                    postResponse.setDownVote(true);
            }
        }
        return postResponse;
    }

    private Post mapToPost(PostRequest postRequest) {
        Community community = communityRepository.findByName(postRequest.getCommunityName())
                .orElseThrow(() -> new CommunityNotFoundException(COMMUNITY_NOT_FOUND_WITH_NAME
                        + postRequest.getCommunityName()));
        return Post.builder()
                .postName(postRequest.getPostName())
                .description(postRequest.getDescription())
                .url(postRequest.getUrl())
                .createdDate(Instant.now())
                .voteCount(0)
                .community(community)
                .user(authService.getCurrentUser())
                .build();
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
