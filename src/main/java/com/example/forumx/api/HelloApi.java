package com.example.forumx.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class HelloApi {
    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }
}
