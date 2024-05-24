package org.example;

import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfiguration;
import org.example.model.QuantityRange;
import org.example.pricingcriteria.BasePriceModel;
import org.example.pricingcriteria.FlatPriceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlatPriceModelTest {

    BasePriceModel priceModel;
    ProductConfiguration productConfiguration;

    @BeforeEach
    public void init() {
        priceModel = new FlatPriceModel(null);
        productConfiguration = new ProductConfiguration(
                "PRODUCT-CODE-1001",
                List.of(
                        new PricingTier(
                                new QuantityRange(1, 10),
                                new BigDecimal("100"),
                                PriceModel.FLAT
                        ),
                        new PricingTier(
                                new QuantityRange(11, 20),
                                new BigDecimal("150"),
                                PriceModel.FLAT
                        )
                )
        );
    }

    @Test
    public void testFlatPriceModelProductPrice() {
        assertEquals("100", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(0), 1));
        assertEquals("100", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(0), 5));
        assertEquals("100", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(0), 10));

        assertEquals("150", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(1), 15));
        assertEquals("150", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(1), 16));
        assertEquals("150", priceModel.calculatePrice(productConfiguration, productConfiguration.tiers().get(1), 20));
    }
}
