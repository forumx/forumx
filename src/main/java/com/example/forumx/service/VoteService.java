package com.example.forumx.service;

import com.example.forumx.entity.VoteEntity;
import com.example.forumx.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Long getUpVoteCountByThread(Long threadId) {
        return voteRepository.countByThreadIdAndStatus(threadId, 1);
    }

    public Long getDownVoteCountByThread(Long threadId) {
        return voteRepository.countByThreadIdAndStatus(threadId, -1);
    }

    public void deleteVote(Long threadId, Long userId) {
        VoteEntity voteEntity = voteRepository.findByThreadIdAndUserId(threadId, userId).orElse(null);
        if(voteEntity != null) {
            voteRepository.delete(voteEntity);
        }
    }

    public void saveVote(Long threadId, Long userId, int status) {
        VoteEntity voteEntity = voteRepository.findByThreadIdAndUserId(threadId, userId).orElse(null);
        if(voteEntity != null) {
            voteEntity.setStatus(status);
            voteRepository.save(voteEntity);
        } else {
            VoteEntity newVote = new VoteEntity();
            newVote.setThreadId(threadId);
            newVote.setStatus(status);
            newVote.setUserId(userId);
            voteRepository.save(newVote);
        }
    }

    public int getVoteStatus(Long threadId, Long userId) {
        VoteEntity voteEntity = voteRepository.findByThreadIdAndUserId(threadId, userId).orElse(null);
        if(voteEntity != null) {
            return voteEntity.getStatus();
        }
        return 0;
    }
}
