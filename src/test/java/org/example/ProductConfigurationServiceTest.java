package org.example;

import org.example.exception.InvalidPriceModelException;
import org.example.exception.InvalidProductIdException;
import org.example.exception.InvalidQuantityRangeException;
import org.example.exception.TierEmptyException;
import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfigRequest;
import org.example.model.ProductConfiguration;
import org.example.model.QuantityRange;
import org.example.service.ProductConfigurationService;
import org.example.service.ProductConfigurationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductConfigurationServiceTest {

    ProductConfigurationService productConfigurationService;
    List<PricingTier> pricingTiers;

    @BeforeEach
    public void setup() {
        productConfigurationService = new ProductConfigurationServiceImpl();
        pricingTiers = List.of(
                new PricingTier(
                        new QuantityRange(1, 10),
                        new BigDecimal("100"),
                        PriceModel.FLAT
                ),
                new PricingTier(
                        new QuantityRange(11, 20),
                        new BigDecimal("150"),
                        PriceModel.VOLUME
                )
        );
    }

    @Test
    public void testAddNewProductConfigPricingTierMissing() {
        assertThrows(TierEmptyException.class, () -> new ProductConfigRequest("PRODUCT-CODE-1001", List.of()));
    }

    @Test
    public void testInvalidQuantityRange() {
        assertThrows(InvalidQuantityRangeException.class, () -> {
            new PricingTier(
                    new QuantityRange(1, -10),
                    new BigDecimal("100"),
                    PriceModel.FLAT
            );
        });
    }

    @Test
    public void testOverlappingQuantityRange() {
        var overlappingTiers = List.of(
                new PricingTier(
                        new QuantityRange(1, 10),
                        new BigDecimal("100"),
                        PriceModel.FLAT
                ),
                new PricingTier(
                        new QuantityRange(5, 20),
                        new BigDecimal("150"),
                        PriceModel.VOLUME
                )
        );
        assertThrows(InvalidQuantityRangeException.class, () -> {
            var productConfigRequest = new ProductConfigRequest("PRODUCT-CODE-1001", overlappingTiers);
            productConfigurationService.addNewProductConfiguration(productConfigRequest);
        });
    }

    @Test
    public void testInvalidPricingModel() {
        var tiers = List.of(
                new PricingTier(
                        new QuantityRange(1, 10),
                        new BigDecimal("100"),
                        PriceModel.GRADUATE
                ),
                new PricingTier(
                        new QuantityRange(11, 20),
                        new BigDecimal("150"),
                        PriceModel.VOLUME
                )
        );
        assertThrows(InvalidPriceModelException.class, () -> {
            var productConfigRequest = new ProductConfigRequest("PRODUCT-CODE-1001", tiers);
            productConfigurationService.addNewProductConfiguration(productConfigRequest);
        });
    }

    @Test
    public void testProductConfigNotFound() {
        Optional<ProductConfiguration> productConfigOptional = productConfigurationService.getProductConfigByProductId("PRODUCT-CODE-1001");
        assertTrue(productConfigOptional.isEmpty());
    }

    @Test
    public void testAddNewProductConfigProductIdMissing() {
        assertThrows(InvalidProductIdException.class, () -> new ProductConfigRequest("", pricingTiers));
    }

    @Test
    public void testAddNewProductConfig() {
        ProductConfigRequest productConfigRequest = new ProductConfigRequest("PRODUCT-CODE-1001", pricingTiers);
        productConfigurationService.addNewProductConfiguration(productConfigRequest);
        Optional<ProductConfiguration> productConfigOptional = productConfigurationService.getProductConfigByProductId("PRODUCT-CODE-1001");
        assertFalse(productConfigOptional.isEmpty());
        assertEquals("PRODUCT-CODE-1001", productConfigOptional.get().productId());
        assertArrayEquals(pricingTiers.toArray(new PricingTier[0]), productConfigOptional.get().tiers().toArray());
    }
}
