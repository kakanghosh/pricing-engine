package org.example.model;

import org.example.exception.InvalidPriceException;

import java.math.BigDecimal;

public record PricingTier(QuantityRange range, BigDecimal value, PriceModel priceModel) {
    public PricingTier {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPriceException("Price: " + value);
        }
    }

    public boolean isValidRange(int quantity) {
        return quantity >= range.from() && quantity <= range.to();
    }

    public int totalQuantity() {
        return range.to() - range.from() + 1;
    }
}
