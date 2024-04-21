package com.example.forumx.repository;

import com.example.forumx.entity.ThreadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Long> {

    Page<ThreadEntity> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<ThreadEntity> findAllByUserId(Long userId, Pageable pageable);
}
