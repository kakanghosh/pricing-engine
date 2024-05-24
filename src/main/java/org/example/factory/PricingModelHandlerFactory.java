package org.example.factory;

import org.example.pricingcriteria.BasePriceModel;
import org.example.pricingcriteria.FlatPriceModel;
import org.example.pricingcriteria.GraduatePriceModel;
import org.example.pricingcriteria.VolumePriceModel;

public class PricingModelHandlerFactory {
    private final BasePriceModel priceModel;

    public PricingModelHandlerFactory() {
        priceModel = new FlatPriceModel(
                new VolumePriceModel(
                        new GraduatePriceModel(null)
                )
        );
    }

    public BasePriceModel getPriceModel() {
        return priceModel;
    }
}
