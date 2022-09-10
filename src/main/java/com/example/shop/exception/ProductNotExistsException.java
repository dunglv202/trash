package com.example.shop.exception;

public class ProductNotExistsException extends RuntimeException {
    public ProductNotExistsException(String message) {
        super("Product does not exists: " + message);
    }
}
