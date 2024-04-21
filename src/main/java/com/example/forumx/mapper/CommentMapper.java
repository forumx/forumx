package com.example.forumx.mapper;

import com.example.forumx.entity.CommentEntity;
import com.example.forumx.model.CommentModel;
import com.example.forumx.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    //save comment new
    public CommentEntity mapCommentModelToEntity(CommentModel commentModel) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentModel.getContent());
        commentEntity.setUserId(commentModel.getUser().getId());
        commentEntity.setThreadId(commentModel.getThreadId());
        commentEntity.setReplyToId(commentModel.getReplyToId());
        return commentEntity;
    }

    //update comment
    public CommentEntity mapCommentModelToEntity(CommentEntity commentEntity, CommentModel commentModel) {
        commentEntity.setContent(commentModel.getContent());
        return commentEntity;
    }

    public CommentModel mapCommentEntityToModel(CommentEntity commentEntity, UserModel userModel) {
        CommentModel commentModel = new CommentModel();
        commentModel.setContent(commentEntity.getContent());
        commentModel.setThreadId(commentEntity.getThreadId());
        commentModel.setReplyToId(commentEntity.getReplyToId());
        commentModel.setCreatedAt(commentEntity.getCreatedAt());
        commentModel.setUpdatedAt(commentEntity.getUpdatedAt());
        commentModel.setId(commentEntity.getId());
        commentModel.setUser(userModel);
        return commentModel;
    }
}
