package com.example.forumx.service;

import com.example.forumx.entity.CommentEntity;
import com.example.forumx.exception.NotFoundException;
import com.example.forumx.mapper.CommentMapper;
import com.example.forumx.model.CommentModel;
import com.example.forumx.model.UserModel;
import com.example.forumx.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


//xoa comment thi tim replyTo, cho gia tri = 0
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;


    @Autowired
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, UserService userService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;

    }

    public Long getCommentCount(Long threadId) {
        return commentRepository.countByThreadId(threadId);
    }

    public Page<CommentModel> getCommentByThread(Long threadId, int currentPage, int pageSize) {
        Sort sortBy = Sort.by("createdAt").ascending();
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sortBy);
        Page<CommentEntity> commentEntities = commentRepository.findByThreadId(threadId, pageRequest);
        List<Long> userIds = commentEntities.stream().map(CommentEntity::getUserId).distinct().toList();
        List<UserModel> users = userService.getUsersById(userIds);

        return commentEntities.map(commentEntity -> {
           UserModel user = users.stream().filter(u -> u.getId().equals(commentEntity.getUserId())).findFirst().orElse(null);
           return commentMapper.mapCommentEntityToModel(commentEntity, user);
        });
    }

    public void createComment(CommentModel commentModel) {
        UserModel userModel = userService.fetchUser(commentModel.getUser().getId());
        if (userModel == null) {
            throw new NotFoundException("User does not exist");
        }
        if(commentModel.getReplyToId() != null){
            if (!commentRepository.existsById(commentModel.getReplyToId())) {
                throw new NotFoundException("Can't reply to comment not existed");
            }
        }
        CommentEntity commentEntity = commentMapper.mapCommentModelToEntity(commentModel);
        commentRepository.save(commentEntity);

    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Comment does not exist");
        }
        getAllReplyComment(commentId).forEach(commentEntity -> {
            commentEntity.setReplyToId(0L);
            commentRepository.save(commentEntity);
        });
        commentRepository.deleteById(commentId);
    }

    public CommentModel updateComment(CommentModel commentModel, Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        if (commentEntity == null) {
            throw new NotFoundException("Comment does not exist");
        }

        UserModel userModel = userService.fetchUser(commentModel.getUser().getId());
        if (userModel == null) {
            throw new NotFoundException("User does not exist");
        }
        commentEntity = commentMapper.mapCommentModelToEntity(commentEntity, commentModel);
        commentEntity = commentRepository.save(commentEntity);
        return commentMapper.mapCommentEntityToModel(commentEntity, userModel);
    }

    public CommentModel getComment(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        if (commentEntity == null) {
            throw new NotFoundException("Comment does not exist");
        }
        UserModel userModel = userService.fetchUser(commentEntity.getUserId());
        return commentMapper.mapCommentEntityToModel(commentEntity, userModel);
    }

    public List<CommentEntity> getAllReplyComment(Long commentId) {
        return commentRepository.findByReplyToId(commentId);
    }

    public void deleteAllCommentsByThread(Long threadId) {
        commentRepository.deleteByThreadId(threadId);
    }
}

