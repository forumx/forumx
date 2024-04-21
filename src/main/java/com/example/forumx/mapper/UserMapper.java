package com.example.forumx.mapper;

import com.example.forumx.entity.UserEntity;
import com.example.forumx.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity mapUserModelToEntity(UserModel userModel) {
        return new ModelMapper().map(userModel, UserEntity.class);
    }

    public UserModel mapUserEntityToModel(UserEntity userEntity) {
        return new ModelMapper().map(userEntity, UserModel.class);
    }
}
