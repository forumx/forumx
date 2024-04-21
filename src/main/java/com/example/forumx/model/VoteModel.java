package com.example.forumx.model;

import lombok.Data;

@Data
public class VoteModel {
    private Long id;
    private Long userId;
    private Long threadId;
    private int status;

}
