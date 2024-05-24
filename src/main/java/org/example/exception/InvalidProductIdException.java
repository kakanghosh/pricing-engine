package org.example.exception;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException() {
    }

    public InvalidProductIdException(String message) {
        super(message);
    }
}
