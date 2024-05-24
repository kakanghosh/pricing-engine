package org.example.exception;

public class InvalidPriceModelException extends RuntimeException {
    public InvalidPriceModelException() {
    }

    public InvalidPriceModelException(String message) {
        super(message);
    }
}
