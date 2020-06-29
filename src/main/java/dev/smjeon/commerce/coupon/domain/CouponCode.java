package dev.smjeon.commerce.coupon.domain;

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
        this.value = value;
    }
}
