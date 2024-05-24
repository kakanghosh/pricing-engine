package org.example;

import org.example.exception.InvalidProductException;
import org.example.exception.InvalidProductPricingRequestException;
import org.example.exception.QuantityOutOfRangeException;
import org.example.factory.PricingModelHandlerFactory;
import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfigRequest;
import org.example.model.ProductPriceResponse;
import org.example.model.ProductPricingRequest;
import org.example.model.QuantityRange;
import org.example.service.ProductConfigurationService;
import org.example.service.ProductConfigurationServiceImpl;
import org.example.service.ProductPricingService;
import org.example.service.ProductPricingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductPricingServiceTest {

    ProductConfigurationService productConfigurationService;
    PricingModelHandlerFactory pricingModelHandlerFactory;
    ProductPricingService productPricingService;

    @BeforeEach
    public void setup() {
        productConfigurationService = new ProductConfigurationServiceImpl();
        productConfigurationService.addNewProductConfiguration(new ProductConfigRequest("PRODUCT-CODE-1001", List.of(
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
        )));
        productConfigurationService.addNewProductConfiguration(new ProductConfigRequest("PRODUCT-CODE-1002", List.of(
                new PricingTier(
                        new QuantityRange(1, 15),
                        new BigDecimal("15"),
                        PriceModel.GRADUATE
                )
        )));
        productConfigurationService.addNewProductConfiguration(new ProductConfigRequest("PRODUCT-CODE-1003", List.of(
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
        )));
        pricingModelHandlerFactory = new PricingModelHandlerFactory();
        productPricingService = new ProductPricingServiceImpl(productConfigurationService, pricingModelHandlerFactory);
    }

    @Test
    public void testProductPriceForInvalidProductId() {
        assertThrows(InvalidProductPricingRequestException.class, () -> new ProductPricingRequest(null, 10));
        assertThrows(InvalidProductPricingRequestException.class, () -> new ProductPricingRequest("", 10));
    }

    @Test
    public void testProductPriceForMissingProductId() {
        var request = new ProductPricingRequest("PRODUCT-CODE-100001", 10);
        assertThrows(InvalidProductException.class, () -> productPricingService.getProductPrice(request));
    }

    @Test
    public void testQuantityOutOfRange() {
        var request = new ProductPricingRequest("PRODUCT-CODE-1001", 100);
        assertThrows(QuantityOutOfRangeException.class, () -> productPricingService.getProductPrice(request));
    }

    @Test
    public void testFlatPriceModelProductPrice() {
        assertEquals(new ProductPriceResponse("100"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1001", 1)));
        assertEquals(new ProductPriceResponse("100"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1001", 5)));
        assertEquals(new ProductPriceResponse("100"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1001", 10)));
    }

    @Test
    public void testVolumePriceModelProductPrice() {
        assertEquals(new ProductPriceResponse("2250"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1001", 15)));
        assertEquals(new ProductPriceResponse("2400"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1001", 16)));
        assertEquals(new ProductPriceResponse("3000"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1001", 20)));
    }

    @Test
    public void testGraduatePriceModelProductPrice() {
        assertEquals(new ProductPriceResponse("150"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1002", 10)));
        assertEquals(new ProductPriceResponse("225"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1002", 15)));

        assertEquals(new ProductPriceResponse("150"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1003", 10)));
        assertEquals(new ProductPriceResponse("250"), productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1003", 15)));
    }

}
