package org.example.exception;

public class TierEmptyException extends RuntimeException {
    public TierEmptyException() {
    }

    public TierEmptyException(String message) {
        super(message);
    }
}
