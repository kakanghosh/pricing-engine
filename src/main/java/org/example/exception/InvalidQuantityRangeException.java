package org.example.exception;

public class InvalidQuantityRangeException extends RuntimeException {
    public InvalidQuantityRangeException() {
    }

    public InvalidQuantityRangeException(String message) {
        super(message);
    }
}
