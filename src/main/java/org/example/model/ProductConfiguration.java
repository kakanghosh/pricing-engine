package org.example.model;

import java.util.List;
import java.util.Optional;

public record ProductConfiguration(String productId, List<PricingTier> tiers) {
    public ProductConfiguration(String productId, List<PricingTier> tiers) {
        this.productId = productId;
        this.tiers = List.copyOf(tiers);
    }

    public Optional<PricingTier> findPricingTierByQuantity(int quantity) {
        return tiers.stream()
                    .filter(pricingTier -> pricingTier.isValidRange(quantity))
                    .findFirst();
    }
}
