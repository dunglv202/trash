package com.example.shop.exception;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String message) {
        super("Insufficient quantity: " + message);
    }
}
