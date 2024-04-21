package com.example.forumx.repository;

import com.example.forumx.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

    Long countByThreadIdAndStatus(Long threadId, int status);
    Optional<VoteEntity> findByThreadIdAndUserId(Long threadId, Long userId);
}
