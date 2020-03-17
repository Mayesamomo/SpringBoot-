package com.repostit.repostit.Repository;

import com.repostit.repostit.Dao.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
