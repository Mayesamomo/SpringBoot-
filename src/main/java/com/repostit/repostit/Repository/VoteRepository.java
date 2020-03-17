package com.repostit.repostit.Repository;

import com.repostit.repostit.Dao.Post;
import com.repostit.repostit.Dao.User;
import com.repostit.repostit.Dao.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
