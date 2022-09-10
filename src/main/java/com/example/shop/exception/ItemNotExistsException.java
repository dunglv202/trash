package com.example.shop.exception;

public class ItemNotExistsException extends RuntimeException {
    public ItemNotExistsException(String message) {
        super("Item does not exist: " + message);
    }
}
