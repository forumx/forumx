package com.example.forumx.model;

import lombok.Data;

@Data
public class UserModel {
    private Long id;

    private String username;

    private String name;

    private boolean enabled;
}
