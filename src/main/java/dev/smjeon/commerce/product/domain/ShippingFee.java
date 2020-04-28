package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.product.domain.exception.InvalidShippingFeeException;

public class ShippingFee {
    private static final int UPPER_BOUND = 30_000;
    private static final int LOWER_BOUND = 0;

    private final int value;

    public ShippingFee(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value >= UPPER_BOUND || value <= LOWER_BOUND) {
            throw new InvalidShippingFeeException(value);
        }
    }
}
