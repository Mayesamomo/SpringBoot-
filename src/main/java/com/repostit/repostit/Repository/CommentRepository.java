package com.repostit.repostit.Repository;

import com.repostit.repostit.Dao.Comment;
import com.repostit.repostit.Dao.Post;
import com.repostit.repostit.Dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
