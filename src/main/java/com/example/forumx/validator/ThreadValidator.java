package com.example.forumx.validator;

import com.example.forumx.repository.ThreadRepository;
import org.springframework.stereotype.Component;

@Component
public class ThreadValidator {
    private final ThreadRepository threadRepository;

    public ThreadValidator(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public boolean isThreadExist(Long threadId) {
        return threadRepository.existsById(threadId);
    }


}
