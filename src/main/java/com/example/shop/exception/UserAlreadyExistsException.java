package com.example.shop.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super("UserAlreadyExistsException: " + message);
    }
}
