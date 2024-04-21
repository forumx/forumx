package com.example.forumx.repository;

import com.example.forumx.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Long countByThreadId(Long threadId);

    Page<CommentEntity> findByThreadId(Long threadId, Pageable pageable);

    List<CommentEntity> findByReplyToId(Long commentId);

    void deleteByThreadId(Long threadId);
}
