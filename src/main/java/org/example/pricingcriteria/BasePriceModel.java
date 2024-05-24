package org.example.pricingcriteria;

import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfiguration;

import java.math.BigDecimal;

public abstract class BasePriceModel {
    private final BasePriceModel nextHandler;

    public BasePriceModel(BasePriceModel nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected String defaultCalculation(PricingTier currentTier, int quantity) {
        BigDecimal finaPrice = currentTier.value().multiply(new BigDecimal(String.valueOf(quantity)));
        return finaPrice.toString();
    }

    public String calculatePrice(PriceModel priceModel, ProductConfiguration productConfiguration, PricingTier currentTier, int quantity) {
        if (nextHandler != null) {
            return nextHandler.calculatePrice(priceModel, productConfiguration, currentTier, quantity);
        }
        return "";
    }
}
