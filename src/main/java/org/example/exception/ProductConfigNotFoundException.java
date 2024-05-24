package org.example.exception;

public class ProductConfigNotFoundException extends RuntimeException {
    public ProductConfigNotFoundException() {
    }

    public ProductConfigNotFoundException(String message) {
        super(message);
    }
}
