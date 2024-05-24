package org.example.service;

import org.example.model.ProductConfigRequest;
import org.example.model.ProductConfiguration;

import java.util.Optional;

public interface ProductConfigurationService {
    void addNewProductConfiguration(ProductConfigRequest productConfigRequest);

    Optional<ProductConfiguration> getProductConfigByProductId(String productId);
}
