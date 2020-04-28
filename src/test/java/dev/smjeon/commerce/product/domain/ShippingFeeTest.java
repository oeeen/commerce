package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.product.exception.InvalidShippingFeeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShippingFeeTest {
    @Test
    @DisplayName("정상적으로 배송비 책정이 됩니다.")
    void normalShippingFee() {
        assertDoesNotThrow(() -> new ShippingFee(5000));
    }

    @Test
    @DisplayName("배송비는 음수가 불가능합니다.")
    void negativeShippingFee() {
        assertThrows(InvalidShippingFeeException.class, () -> new ShippingFee(-100));
    }

    @Test
    @DisplayName("50,000원 초과의 배송비는 불가능합니다.")
    void tooHighShippingFee() {
        assertThrows(InvalidShippingFeeException.class, () -> new ShippingFee(60_000));
    }
}
