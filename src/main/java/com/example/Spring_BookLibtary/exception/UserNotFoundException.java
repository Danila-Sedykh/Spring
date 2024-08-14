package com.example.Spring_BookLibtary.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String massage){
        super(massage);
    }
}
