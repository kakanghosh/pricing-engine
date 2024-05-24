package org.example.pricingcriteria;

import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfiguration;

public class FlatPriceModel extends BasePriceModel {
    private final PriceModel priceModel;


    public FlatPriceModel(BasePriceModel nextHandler) {
        super(nextHandler);
        priceModel = PriceModel.FLAT;
    }

    @Override
    public String calculatePrice(ProductConfiguration productConfiguration, PricingTier currentTier, int quantity) {
        if (this.priceModel == currentTier.priceModel()) {
            return currentTier.value().toString();
        }
        return super.calculatePrice(productConfiguration, currentTier, quantity);
    }
}
