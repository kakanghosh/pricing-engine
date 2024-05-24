package org.example;

import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfiguration;
import org.example.model.QuantityRange;
import org.example.pricingcriteria.BasePriceModel;
import org.example.pricingcriteria.GraduatePriceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraduatePriceModelTest {

    BasePriceModel priceModel;
    ProductConfiguration productConfiguration1;
    ProductConfiguration productConfiguration2;

    @BeforeEach
    public void init() {
        priceModel = new GraduatePriceModel(null);
        productConfiguration1 = new ProductConfiguration(
                "PRODUCT-CODE-1001",
                List.of(
                        new PricingTier(
                                new QuantityRange(1, 15),
                                new BigDecimal("15"),
                                PriceModel.GRADUATE
                        )
                )

        );
        productConfiguration2 = new ProductConfiguration(
                "PRODUCT-CODE-1002",
                List.of(
                        new PricingTier(
                                new QuantityRange(1, 10),
                                new BigDecimal("15"),
                                PriceModel.GRADUATE
                        ),
                        new PricingTier(
                                new QuantityRange(11, 20),
                                new BigDecimal("20"),
                                PriceModel.GRADUATE
                        )
                )
        );
    }

    @Test
    public void testGraduatePriceModelProductPriceForSingleTier() {
        var currentTier = productConfiguration1.tiers().get(0);
        assertEquals("15", priceModel.calculatePrice(productConfiguration1, currentTier, 1));
        assertEquals("75", priceModel.calculatePrice(productConfiguration1, currentTier, 5));
        assertEquals("150", priceModel.calculatePrice(productConfiguration1, currentTier, 10));
    }

    @Test
    public void testGraduatePriceModelProductPriceForMultipleTier() {
        assertEquals("15", priceModel.calculatePrice(productConfiguration2, productConfiguration2.tiers().get(0), 1));
        assertEquals("75", priceModel.calculatePrice(productConfiguration2, productConfiguration2.tiers().get(0), 5));
        assertEquals("150", priceModel.calculatePrice(productConfiguration2, productConfiguration2.tiers().get(0), 10));
        assertEquals("250", priceModel.calculatePrice(productConfiguration2, productConfiguration2.tiers().get(1), 15));
        assertEquals("270", priceModel.calculatePrice(productConfiguration2, productConfiguration2.tiers().get(1), 16));
        assertEquals("350", priceModel.calculatePrice(productConfiguration2, productConfiguration2.tiers().get(1), 20));
    }
}
