package org.example.service;

import org.example.exception.InvalidProductException;
import org.example.exception.QuantityOutOfRangeException;
import org.example.factory.PricingModelHandlerFactory;
import org.example.model.PricingTier;
import org.example.model.ProductConfiguration;
import org.example.model.ProductPriceResponse;
import org.example.model.ProductPricingRequest;
import org.example.pricingcriteria.BasePriceModel;

import java.util.Optional;

public class ProductPricingServiceImpl implements ProductPricingService {
    private final ProductConfigurationService productConfigurationService;
    private final PricingModelHandlerFactory pricingModelHandlerFactory;

    public ProductPricingServiceImpl(ProductConfigurationService productConfigurationService,
                                     PricingModelHandlerFactory pricingModelHandlerFactory) {
        this.productConfigurationService = productConfigurationService;
        this.pricingModelHandlerFactory = pricingModelHandlerFactory;
    }

    @Override
    public ProductPriceResponse getProductPrice(ProductPricingRequest productPricingRequest) {
        Optional<ProductConfiguration> productConfigOptional = productConfigurationService.getProductConfigByProductId(
                productPricingRequest.productId()
        );
        if (productConfigOptional.isEmpty()) {
            throw new InvalidProductException();
        }
        PricingTier currentPricingTier = getValidCurrentPricingTier(productConfigOptional.get(), productPricingRequest.quantity());
        BasePriceModel priceModel = pricingModelHandlerFactory.getPriceModel();
        String price = priceModel.calculatePrice(
                productConfigOptional.get(),
                currentPricingTier,
                productPricingRequest.quantity()
        );
        return new ProductPriceResponse(price);
    }

    private PricingTier getValidCurrentPricingTier(ProductConfiguration productConfiguration, int quantity) {
        Optional<PricingTier> tierOptional = productConfiguration.findPricingTierByQuantity(quantity);
        if (tierOptional.isEmpty()) {
            throw new QuantityOutOfRangeException("Quantity: " + quantity);
        }
        return tierOptional.get();
    }
}
