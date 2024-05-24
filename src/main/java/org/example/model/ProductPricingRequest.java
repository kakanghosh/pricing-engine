package org.example.model;

import org.example.exception.InvalidProductPricingRequestException;

import java.util.Objects;

public record ProductPricingRequest(String productId, int quantity) {
    public ProductPricingRequest {
        Objects.requireNonNull(productId);
        if (productId.isBlank() || quantity <= 0) {
            throw new InvalidProductPricingRequestException(String.format("productId: %s, quantity: %d", productId, quantity));
        }
    }
}
