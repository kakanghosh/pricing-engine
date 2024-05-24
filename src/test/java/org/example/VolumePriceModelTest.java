package org.example;

import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfiguration;
import org.example.model.QuantityRange;
import org.example.pricingcriteria.BasePriceModel;
import org.example.pricingcriteria.VolumePriceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VolumePriceModelTest {

    BasePriceModel priceModel;
    ProductConfiguration productConfiguration;

    @BeforeEach
    public void init() {
        priceModel = new VolumePriceModel(null);
        productConfiguration = new ProductConfiguration(
                "PRODUCT-CODE-1001",
                List.of(
                        new PricingTier(
                                new QuantityRange(1, 20),
                                new BigDecimal("150"),
                                PriceModel.VOLUME
                        )
                )
        );
    }

    @Test
    public void testVolumePriceModelProductPrice() {
        assertEquals("150", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(0), 1));
        assertEquals("750", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(0), 5));
        assertEquals("1500", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(0), 10));
        assertEquals("3000", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(0), 20));
    }
}
