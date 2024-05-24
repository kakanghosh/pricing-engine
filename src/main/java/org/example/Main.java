package org.example;

import org.example.factory.PricingModelHandlerFactory;
import org.example.model.*;
import org.example.service.ProductConfigurationService;
import org.example.service.ProductConfigurationServiceImpl;
import org.example.service.ProductPricingService;
import org.example.service.ProductPricingServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProductConfigurationService productConfigurationService = new ProductConfigurationServiceImpl();
        productConfigurationService.addNewProductConfiguration(new ProductConfigRequest(
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
                                PriceModel.VOLUME
                        )
                )
        ));
        productConfigurationService.addNewProductConfiguration(new ProductConfigRequest(
                "PRODUCT-CODE-1002",
                List.of(
                        new PricingTier(
                                new QuantityRange(1, 10),
                                new BigDecimal("100"),
                                PriceModel.GRADUATE
                        ),
                        new PricingTier(
                                new QuantityRange(11, 20),
                                new BigDecimal("150"),
                                PriceModel.GRADUATE
                        )
                )
        ));
        PricingModelHandlerFactory pricingModelHandlerFactory = new PricingModelHandlerFactory();
        ProductPricingService productPricingService = new ProductPricingServiceImpl(
                productConfigurationService,
                pricingModelHandlerFactory
        );
        ProductPriceResponse productPrice1 = productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1001", 19));
        ProductPriceResponse productPrice2 = productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1002", 15));
        ProductPriceResponse productPrice3 = productPricingService.getProductPrice(new ProductPricingRequest("PRODUCT-CODE-1001", 5));

        System.out.println(productPrice1);
        System.out.println(productPrice2);
        System.out.println(productPrice3);
    }
}
