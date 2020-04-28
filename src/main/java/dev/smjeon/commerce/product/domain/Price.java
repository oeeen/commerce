package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.product.exception.InvalidPriceException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Embeddable
public class Price {
    private static final int UPPER_BOUND = 100_000_000;
    private static final int LOWER_BOUND = 0;

    @Column(nullable = false, name = "price")
    private int value;

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
