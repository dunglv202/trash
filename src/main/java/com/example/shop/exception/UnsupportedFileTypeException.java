package com.example.shop.exception;

public class UnsupportedFileTypeException extends RuntimeException {
    public UnsupportedFileTypeException(String message) {
        super("UnsupportedFileTypeException: " + message);
    }
}
