package com.example.forumx.api;

import com.example.forumx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserApi {
    private final UserService userService;
    @Autowired
    public UserApi(UserService userService){
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
