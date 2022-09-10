package com.example.shop.exception;

public class CategoryNotExistsException extends RuntimeException {
    public CategoryNotExistsException(String message) {
        super("Category does not exists: " + message);
    }
}
