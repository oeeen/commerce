package dev.smjeon.commerce.coupon.dto;

import dev.smjeon.commerce.coupon.domain.CouponType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponRequest {

    private String couponName;

    private String code;

    private CouponType type;

    private Double rate;

    public CouponRequest(String couponName, String code, CouponType type, Double rate) {
        this.couponName = couponName;
        this.code = code;
        this.type = type;
        this.rate = rate;
    }
}
