package org.example.exception;

public class QuantityOutOfRangeException extends RuntimeException {
    public QuantityOutOfRangeException() {
    }

    public QuantityOutOfRangeException(String message) {
        super(message);
    }
}
