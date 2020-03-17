package com.repostit.repostit.Controller;

import com.repostit.repostit.Dto.CommentDto;
import com.repostit.repostit.Service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllCommentsForPost(@PathVariable Long postId) {
        return commentService.getCommentByPost(postId);
    }

    @GetMapping("/query/user/{userName}")
    public List<CommentDto> getAllCommentsByUser(@PathVariable String username) {
        return commentService.getCommentsByUser(username);
    }

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return new ResponseEntity(OK);
    }
}
