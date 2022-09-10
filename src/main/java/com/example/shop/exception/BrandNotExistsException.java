package com.example.shop.exception;

public class BrandNotExistsException extends RuntimeException {
    public BrandNotExistsException(String message) {
        super("Brand does not exists: " + message);
    }
}
