package org.example.model;

import org.example.exception.InvalidQuantityRangeException;

public record QuantityRange(int from, int to) {
    public QuantityRange {
        if (from <= 0 || to <= 0 || from >= to) {
            throw new InvalidQuantityRangeException(String.format("Range: from: %d to: %d", from, to));
        }
    }

    public boolean isValidRange(int quantity) {
        return quantity >= from && quantity <= to;
    }
}
