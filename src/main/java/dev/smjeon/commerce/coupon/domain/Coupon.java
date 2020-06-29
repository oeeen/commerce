package dev.smjeon.commerce.coupon.domain;

import dev.smjeon.commerce.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Getter
@Entity
public class Coupon extends BaseEntity {

    private String name;

    @Embedded
    private CouponCode code;

    @Enumerated(value = EnumType.STRING)
    private CouponType type;

    private Double rate;

    public Coupon(String name, CouponCode code, CouponType type, Double rate) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.rate = rate;
    }
}
