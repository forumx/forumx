package com.example.forumx.service;

import com.example.forumx.entity.UserEntity;
import com.example.forumx.exception.NotFoundException;
import com.example.forumx.mapper.UserMapper;
import com.example.forumx.model.UserModel;
import com.example.forumx.repository.UserRepository;
import com.example.forumx.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserMapper userMapper;


    @Autowired
    public UserService(UserRepository userRepository, UserValidator userValidator, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    public void createOrUpdateUser(String username, String name, String img) {
        if (!userValidator.isUsernameExist(username)) {
            UserEntity newUserEntity = new UserEntity();
            newUserEntity.setEnabled(true);
            newUserEntity.setUsername(username);
            newUserEntity.setName(name);
            newUserEntity.setImg_url(img);
            userRepository.save(newUserEntity);
        } else {
            UserEntity currentUser = userRepository.findByUsername(username);
            if (!currentUser.getName().equals(name)) {
                currentUser.setName(name);
            }
            if (!currentUser.getImg_url().equals(img)) {
                currentUser.setImg_url(img);
            }
            userRepository.save(currentUser);


        }
    }


    public UserModel fetchUser(Long userId) {
        return userMapper.mapUserEntityToModel(userRepository.findById(userId).orElse(null));
    }

    public List<UserModel> getUsersById(List<Long> userIds) {
        return userRepository.findAllById(userIds).stream().map(userMapper::mapUserEntityToModel).toList();
    }

    public void deleteUser(Long userId){
        UserEntity deletingUser = userRepository.findById(userId).orElse(null);
        if(deletingUser != null){
            deletingUser.setEnabled(false);
            userRepository.save(deletingUser);
        }else{
            throw new NotFoundException("User not found");
        }
    }
}
