package org.example.exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException() {
    }

    public InvalidPriceException(String message) {
        super(message);
    }
}
