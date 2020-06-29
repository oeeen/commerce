package dev.smjeon.commerce.coupon.dto;

import dev.smjeon.commerce.coupon.domain.CouponType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponResponse {

    private Long id;

    private String couponName;

    private String code;

    private CouponType type;

    private Double rate;

    public CouponResponse(Long id, String couponName, String code, CouponType type, Double rate) {
        this.id = id;
        this.couponName = couponName;
        this.code = code;
        this.type = type;
        this.rate = rate;
    }
}
