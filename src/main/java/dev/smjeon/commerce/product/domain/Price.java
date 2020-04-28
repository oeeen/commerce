package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.product.exception.InvalidPriceException;

public class Price {
    private static final int UPPER_BOUND = 100_000_000;
    private static final int LOWER_BOUND = 0;

    private final int value;

    public Price(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value >= UPPER_BOUND || value <= LOWER_BOUND) {
            throw new InvalidPriceException(value);
        }
    }
}
