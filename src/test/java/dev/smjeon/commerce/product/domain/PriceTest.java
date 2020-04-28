package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.product.domain.exception.InvalidPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceTest {
    @Test
    @DisplayName("정상적으로 가격 책정이 가능합니다.")
    void normalPrice() {
        assertDoesNotThrow(() -> new Price(100_000));
    }

    @Test
    @DisplayName("음수 가격은 불가능합니다.")
    void negativePrice() {
        assertThrows(InvalidPriceException.class, () -> new Price(-100));
    }

    @Test
    @DisplayName("1억원 이상의 가격은 불가능합니다.")
    void tooHighPrice() {
        assertThrows(InvalidPriceException.class, () -> new Price(100_000_000));
    }
}
