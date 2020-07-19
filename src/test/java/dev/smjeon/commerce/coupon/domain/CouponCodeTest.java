package dev.smjeon.commerce.coupon.domain;

import dev.smjeon.commerce.coupon.exception.CannotCreateCouponCodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CouponCodeTest {

    @Test
    @DisplayName("15자의 문자로 쿠폰 코드를 생성할 수 있습니다.")
    void create() {
        assertDoesNotThrow(() -> new CouponCode("12345AABBCCDDEE"));
    }

    @Test
    @DisplayName("15자가 아니면 쿠폰 코드가 생성되지 않습니다.")
    void createInvalidLength() {
        assertThrows(CannotCreateCouponCodeException.class, () -> new CouponCode("12345"));
        assertThrows(CannotCreateCouponCodeException.class, () -> new CouponCode("AABBCCDDEE123456"));
    }
}