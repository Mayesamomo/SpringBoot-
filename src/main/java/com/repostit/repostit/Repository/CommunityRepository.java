package com.repostit.repostit.Repository;

import com.repostit.repostit.Dao.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findByName(String communityName);
}
