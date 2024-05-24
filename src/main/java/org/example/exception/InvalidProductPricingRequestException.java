package org.example.exception;

public class InvalidProductPricingRequestException extends RuntimeException {
    public InvalidProductPricingRequestException() {
    }

    public InvalidProductPricingRequestException(String message) {
        super(message);
    }
}
