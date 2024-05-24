package org.example.model;

import org.example.exception.InvalidProductIdException;
import org.example.exception.TierEmptyException;

import java.util.List;
import java.util.Objects;

public record ProductConfigRequest(String productId, List<PricingTier> tiers) {
    public ProductConfigRequest(String productId, List<PricingTier> tiers) {
        if (Objects.isNull(productId) || productId.isBlank()) {
            throw new InvalidProductIdException(productId);
        }
        if (tiers.isEmpty()) {
            throw new TierEmptyException();
        }
        this.productId = productId;
        this.tiers = List.copyOf(tiers);
    }
}
