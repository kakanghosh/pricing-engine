package org.example.pricingcriteria;

import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfiguration;

public class VolumePriceModel extends BasePriceModel {
    private final PriceModel priceModel;


    public VolumePriceModel(BasePriceModel nextHandler) {
        super(nextHandler);
        priceModel = PriceModel.VOLUME;
    }

    @Override
    public String calculatePrice(ProductConfiguration productConfiguration, PricingTier currentTier, int quantity) {
        if (this.priceModel == currentTier.priceModel()) {
            return defaultCalculation(currentTier, quantity);
        }
        return super.calculatePrice(productConfiguration, currentTier, quantity);
    }
}
