package org.example.service;

import org.example.model.ProductPriceResponse;
import org.example.model.ProductPricingRequest;

public interface ProductPricingService {

    ProductPriceResponse getProductPrice(ProductPricingRequest productPricingRequest);
}
