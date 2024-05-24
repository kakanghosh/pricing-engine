package org.example.model;

import org.example.exception.InvalidProductIdException;

import java.util.List;
import java.util.Objects;

public record ProductConfigRequest(String productId, List<PricingTier> tiers) {
    public ProductConfigRequest(String productId, List<PricingTier> tiers) {
        Objects.requireNonNull(productId);
        if (productId.isBlank()) {
            throw new InvalidProductIdException(productId);
        }
        this.productId = productId;
        this.tiers = List.copyOf(tiers);
    }
}
