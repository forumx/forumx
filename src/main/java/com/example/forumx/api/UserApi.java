package com.example.forumx.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserApi {
    @GetMapping("/admin/greetMe")
    public String greetMe() {
        return "Hello Admin!";
    }

    @GetMapping("/user/greetMe")
    public String greetUser() {
        return "Hello User!";
    }
}
