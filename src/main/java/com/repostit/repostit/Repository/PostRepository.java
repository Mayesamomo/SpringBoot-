package com.repostit.repostit.Repository;

import com.repostit.repostit.Dao.Community;
import com.repostit.repostit.Dao.Post;
import com.repostit.repostit.Dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCommunity(Community community);

    List<Post> findByUser(User user);
}
