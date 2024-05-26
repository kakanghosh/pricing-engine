package org.example.pricingcriteria;

import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfiguration;

import java.math.BigDecimal;

public class GraduatePriceModel extends BasePriceModel {
    private final PriceModel priceModel;

    public GraduatePriceModel(BasePriceModel nextHandler) {
        super(nextHandler);
        priceModel = PriceModel.GRADUATE;
    }

    @Override
    public String calculatePrice(ProductConfiguration productConfiguration, PricingTier currentTier, int quantity) {
        if (this.priceModel == currentTier.priceModel()) {
            if (productConfiguration.tiers().size() > 1) {
                return calculateUsingMultipleRange(productConfiguration, quantity);
            }
            return defaultCalculation(currentTier, quantity);
        }
        return super.calculatePrice(productConfiguration, currentTier, quantity);
    }

    private String calculateUsingMultipleRange(ProductConfiguration productConfiguration, int quantity) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        int tierIndex = 0;
        while (quantity > 0) {
            PricingTier currentTier = productConfiguration.tiers().get(tierIndex);
            int currentQuantity = Math.min(currentTier.totalQuantity(), quantity);
            totalPrice = totalPrice.add(new BigDecimal(defaultCalculation(currentTier, currentQuantity)));
            quantity -= currentQuantity;
            tierIndex++;
        }
        return totalPrice.toString();
    }
}
