package com.example.Spring_BookLibtary.exception;

public class BookNotFoundException extends Exception{
    public BookNotFoundException(String massage){
        super(massage);
    }
}
