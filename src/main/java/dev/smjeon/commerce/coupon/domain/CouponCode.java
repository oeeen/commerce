package dev.smjeon.commerce.coupon.domain;

import dev.smjeon.commerce.coupon.exception.CannotCreateCouponCodeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "value")
@Embeddable
public class CouponCode {

    private static final int CODE_LENGTH = 15;

    private String value;

    public CouponCode(String value) {
        if (value.length() != CODE_LENGTH) {
            throw new CannotCreateCouponCodeException(value);
        }
        this.value = value;
    }
}
