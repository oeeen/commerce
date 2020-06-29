package dev.smjeon.commerce.coupon.converter;

import dev.smjeon.commerce.coupon.domain.Coupon;
import dev.smjeon.commerce.coupon.dto.CouponResponse;

public class CouponConverter {
    private CouponConverter() {
    }

    public static CouponResponse toDto(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode().getValue(),
                coupon.getType(),
                coupon.getRate()
        );
    }
}
