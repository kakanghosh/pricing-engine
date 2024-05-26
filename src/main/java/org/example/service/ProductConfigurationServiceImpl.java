package org.example.service;

import org.example.exception.InvalidPriceModelException;
import org.example.exception.InvalidQuantityRangeException;
import org.example.exception.TierEmptyException;
import org.example.model.PriceModel;
import org.example.model.PricingTier;
import org.example.model.ProductConfigRequest;
import org.example.model.ProductConfiguration;
import org.example.model.QuantityRange;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductConfigurationServiceImpl implements ProductConfigurationService {

    private final Map<String, ProductConfiguration> productConfigMap;

    public ProductConfigurationServiceImpl() {
        productConfigMap = new HashMap<>();
    }

    @Override
    public void addNewProductConfiguration(ProductConfigRequest productConfigRequest) {
        validateTierQuantityRange(productConfigRequest.tiers());
        validateGraduatedTierConfig(productConfigRequest.tiers());

        ProductConfiguration productConfiguration = new ProductConfiguration(
                productConfigRequest.productId(),
                productConfigRequest.tiers().stream()
                                    .sorted(Comparator.comparingInt(tier -> tier.range().from()))
                                    .toList()
        );
        productConfigMap.put(productConfigRequest.productId(), productConfiguration);
    }

    @Override
    public Optional<ProductConfiguration> getProductConfigByProductId(String productId) {
        if (!productConfigMap.containsKey(productId)) {
            return Optional.empty();
        }
        return Optional.of(productConfigMap.get(productId));
    }

    private void validateGraduatedTierConfig(List<PricingTier> tiers) {
        if (tiers.isEmpty()) {
            throw new TierEmptyException();
        }
        Optional<PricingTier> graduateTierOptional = tiers.stream()
                                                          .filter(pricingTier -> pricingTier.priceModel() == PriceModel.GRADUATE)
                                                          .findFirst();
        Optional<PricingTier> nonGraduateTierOptional = tiers.stream()
                                                             .filter(pricingTier -> pricingTier.priceModel() != PriceModel.GRADUATE)
                                                             .findFirst();
        if (tiers.size() > 1 && graduateTierOptional.isPresent() && nonGraduateTierOptional.isPresent()) {
            throw new InvalidPriceModelException("Graduate can not co-exists with other price model");
        }
    }

    private void validateTierQuantityRange(List<PricingTier> tiers) {
        if (tiers.isEmpty()) {
            throw new TierEmptyException();
        }
        List<QuantityRange> quantityRanges = tiers.stream().map(PricingTier::range)
                                                  .sorted(Comparator.comparingInt(QuantityRange::from))
                                                  .toList();
        LinkedList<QuantityRange> validRageList = new LinkedList<>();
        validRageList.add(quantityRanges.get(0));
        int tierSize = quantityRanges.size();
        for (int i = 1; i < tierSize; i++) {
            QuantityRange currentRange = quantityRanges.get(i);
            if (validRageList.getLast().to() + 1 != currentRange.from()) {
                throw new InvalidQuantityRangeException("Range: " + currentRange);
            }
            validRageList.add(currentRange);
        }
    }
}
