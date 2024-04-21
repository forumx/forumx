package com.example.forumx.service;

import com.example.forumx.entity.CategoryEntity;
import com.example.forumx.entity.ThreadEntity;
import com.example.forumx.exception.NotFoundException;
import com.example.forumx.mapper.ThreadMapper;
import com.example.forumx.model.CategoryModel;
import com.example.forumx.model.ThreadModel;
import com.example.forumx.model.UserModel;
import com.example.forumx.repository.ThreadRepository;
import com.example.forumx.validator.CategoryValidator;
import com.example.forumx.validator.ThreadValidator;
import com.example.forumx.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {
    private final ThreadRepository threadRepository;
    private final ThreadValidator threadValidator;
    private final UserValidator userValidator;
    private final CategoryValidator categoryValidator;
    private final ThreadMapper threadMapper;
    private final UserService userService;

    private final CategoryService categoryService;

    private final CommentService commentService;

    private final VoteService voteService;


    @Autowired
    public ThreadService(ThreadRepository threadRepository, ThreadValidator threadValidator, UserValidator userValidator, CommentService commentService,
                         CategoryValidator categoryValidator, ThreadMapper threadMapper, UserService userService, CategoryService categoryService,
                         VoteService voteService) {
        this.threadRepository = threadRepository;
        this.threadValidator = threadValidator;
        this.userValidator = userValidator;
        this.categoryValidator = categoryValidator;
        this.threadMapper = threadMapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.voteService = voteService;
    }

    public void createThread(ThreadModel threadModel) {
        UserModel userModel = userService.fetchUser(threadModel.getUser().getId());
        if (userModel == null) {
            throw new NotFoundException("User does not exist");
        }
        if (categoryValidator.isCategoryExist(threadModel.getCategory().getId())) {
            throw new NotFoundException("Category does not exist");
        }
        ThreadEntity threadEntity = threadMapper.mapThreadModelToEntity(threadModel);
        threadRepository.save(threadEntity);
        categoryService.updateThreadCount(threadModel.getCategory().getId());
    }

    public ThreadModel updateThread(ThreadModel threadModel, Long threadId) {
        ThreadEntity threadEntity = threadRepository.findById(threadId).orElse(null);
        if (threadEntity == null) {
            throw new NotFoundException("Thread does not exist");
        }

        threadEntity = threadRepository.save(threadMapper.mapThreadModelToEntity(threadEntity,threadModel));
        UserModel userModel = userService.fetchUser(threadEntity.getUserId());
        if(userModel == null) {
            throw new NotFoundException("User does not exist");
        }
        CategoryModel categoryModel = categoryService.getCategory(threadEntity.getCategoryId());

        return threadMapper.mapThreadEntityToModel(threadEntity, categoryModel, userModel, commentService.getCommentCount(threadEntity.getId()),
                voteService.getUpVoteCountByThread(threadEntity.getId()), voteService.getDownVoteCountByThread(threadEntity.getId()));
    }

    public ThreadModel getThread(Long threadId) {
        if (!threadValidator.isThreadExist(threadId)) {
            throw new NotFoundException("Thread does not exist");
        }
        ThreadEntity threadEntity = threadRepository.findById(threadId).orElse(null);
        assert threadEntity != null;
        UserModel userModel = userService.fetchUser(threadEntity.getUserId());
        CategoryModel categoryModel = categoryService.fetchCategory(threadEntity.getCategoryId());

        return threadMapper.mapThreadEntityToModel(threadEntity, categoryModel, userModel, commentService.getCommentCount(threadEntity.getId()),
                voteService.getUpVoteCountByThread(threadEntity.getId()), voteService.getDownVoteCountByThread(threadEntity.getId()));
    }

    public void deleteThread(Long threadId) {

        ThreadEntity threadEntity = threadRepository.findById(threadId).orElse(null);
        if (threadEntity == null) {
            throw new NotFoundException("Thread does not exist");
        }

        commentService.deleteAllCommentsByThread(threadId);

        threadRepository.deleteById(threadId);
    }

    public Page<ThreadModel> getAllThreads(int currentPage, int pageSize) {
        Sort sortBy = Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sortBy);
        Page<ThreadEntity> threadEntities = threadRepository.findAll(pageRequest);
        List<Long> userIds = threadEntities.stream()
                .map(ThreadEntity::getUserId)
                .distinct()
                .toList();


        List<UserModel> userModels = userService.getUsersById(userIds);
        List<Long> categoryIds = threadEntities.stream()
                .map(ThreadEntity::getCategoryId)
                .distinct()
                .toList();
        List<CategoryModel> categoryModels = categoryService.getCategoriesById(categoryIds);

        return threadEntities.map(threadEntity -> {
            UserModel userModel = userModels.stream()
                    .filter(u -> u.getId().equals(threadEntity.getUserId()))
                    .findFirst()
                    .orElse(null);
            CategoryModel categoryModel = categoryModels.stream()
                    .filter(c -> c.getId().equals(threadEntity.getCategoryId()))
                    .findFirst()
                    .orElse(null);

            return threadMapper.mapThreadEntityToModelForList(threadEntity, categoryModel, userModel, commentService.getCommentCount(threadEntity.getId()),
                    voteService.getUpVoteCountByThread(threadEntity.getId()), voteService.getDownVoteCountByThread(threadEntity.getId()));
        });
    }

    public Page<ThreadModel> getThreadsByCategory(Long categoryId, int currentPage, int pageSize) {
        if (!categoryValidator.isCategoryExist(categoryId)) {
            throw new NotFoundException("Category does not exist");
        }
        Sort sortBy = Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sortBy);
        Page<ThreadEntity> threadEntities = threadRepository.findAllByCategoryId(categoryId, pageRequest);

        List<Long> userIds = threadEntities.stream()
                .map(ThreadEntity::getUserId)
                .distinct()
                .toList();

        List<UserModel> userModels = userService.getUsersById(userIds);


        CategoryModel categoryModel = categoryService.fetchCategory(categoryId);

        return threadEntities.map(threadEntity -> {
            UserModel userModel = userModels.stream()
                    .filter(u -> u.getId().equals(threadEntity.getUserId()))
                    .findFirst()
                    .orElse(null);
            return threadMapper.mapThreadEntityToModelForList(threadEntity, categoryModel, userModel, commentService.getCommentCount(threadEntity.getId()),
                    voteService.getUpVoteCountByThread(threadEntity.getId()), voteService.getDownVoteCountByThread(threadEntity.getId()));
        });
    }

    public Page<ThreadModel> getThreadsByUser(Long userId, int currentPage, int pageSize) {

        UserModel userModel = userService.fetchUser(userId);


        Sort sortBy = Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sortBy);
        Page<ThreadEntity> threadEntities = threadRepository.findAllByUserId(userId, pageRequest);

        List<Long> categoryIds = threadEntities.stream()
                .map(ThreadEntity::getCategoryId)
                .distinct()
                .toList();
        List<CategoryModel> categoryModels = categoryService.getCategoriesById(categoryIds);



        return threadEntities.map(threadEntity -> {
            CategoryModel categoryModel = categoryModels.stream()
                    .filter(c -> c.getId().equals(threadEntity.getCategoryId()))
                    .findFirst()
                    .orElse(null);
            return threadMapper.mapThreadEntityToModelForList(threadEntity, categoryModel, userModel, commentService.getCommentCount(threadEntity.getId()),
                    voteService.getUpVoteCountByThread(threadEntity.getId()), voteService.getDownVoteCountByThread(threadEntity.getId()));
        });
    }


}
