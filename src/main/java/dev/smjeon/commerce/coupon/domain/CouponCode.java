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

    private String value;

    public CouponCode(String value) {
        if (value.length() != 15) {
            throw new CannotCreateCouponCodeException(value);
        }
        this.value = value;
    }
}
