package com.example.forumx.mapper;

import com.example.forumx.entity.ThreadEntity;
import com.example.forumx.model.CategoryModel;
import com.example.forumx.model.ThreadModel;
import com.example.forumx.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class ThreadMapper {

    //save model vao database new
    public ThreadEntity mapThreadModelToEntity(ThreadModel threadModel) {
        ThreadEntity threadEntity = new ThreadEntity();
        threadEntity.setContent(threadModel.getContent());
        threadEntity.setTitle(threadModel.getTitle());
        threadEntity.setUserId(threadModel.getUser().getId());
        threadEntity.setCategoryId(threadModel.getCategory().getId());
        return threadEntity;
    }

    //update model vao database
    public ThreadEntity mapThreadModelToEntity(ThreadEntity threadEntity,ThreadModel threadModel) {
        threadEntity.setContent(threadModel.getContent());
        threadEntity.setTitle(threadModel.getTitle());
        return threadEntity;
    }

    //lay du lieu tu entity ra model
    public ThreadModel mapThreadEntityToModel(ThreadEntity threadEntity, CategoryModel categoryModel, UserModel userModel,
                                              Long commentCount, Long upVoteCount, Long downVoteCount) {
        ThreadModel threadModel = new ThreadModel();
        threadModel.setContent(threadEntity.getContent());
        threadModel.setTitle(threadEntity.getTitle());
        threadModel.setCategory(categoryModel);
        threadModel.setUser(userModel);
        threadModel.setCreatedAt(threadEntity.getCreatedAt());
        threadModel.setUpdatedAt(threadEntity.getUpdatedAt());
        threadModel.setId(threadEntity.getId());
        threadModel.setCommentCount(commentCount);
        threadModel.setDownVoteCount(downVoteCount);
        threadModel.setDownVoteCount(upVoteCount);
        return threadModel;
    }

    public ThreadModel mapThreadEntityToModelForList(ThreadEntity threadEntity, CategoryModel categoryModel, UserModel userModel,
                                              Long commentCount, Long upVoteCount, Long downVoteCount) {
        ThreadModel threadModel = new ThreadModel();
        threadModel.setTitle(threadEntity.getTitle());
        threadModel.setCategory(categoryModel);
        threadModel.setUser(userModel);
        threadModel.setCreatedAt(threadEntity.getCreatedAt());
        threadModel.setUpdatedAt(threadEntity.getUpdatedAt());
        threadModel.setId(threadEntity.getId());
        threadModel.setCommentCount(commentCount);
        threadModel.setDownVoteCount(downVoteCount);
        threadModel.setDownVoteCount(upVoteCount);
        return threadModel;
    }


}
