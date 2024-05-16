package com.example.forumx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ThreadModel {
    private Long id;

    private String title;

    private String content;

    private UserModel user;

    @JsonIncludeProperties({"id","name"})
    private CategoryModel category;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;

    private Long commentCount;

    private Long upVoteCount;

    private Long downVoteCount;
}
