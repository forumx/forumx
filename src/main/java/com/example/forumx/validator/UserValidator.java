package com.example.forumx.validator;

import com.example.forumx.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    public boolean isUsernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
