package com.example.forumx.exception;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String msg){
        super(msg);
    }
}
