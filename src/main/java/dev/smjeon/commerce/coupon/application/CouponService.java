package dev.smjeon.commerce.coupon.application;

import dev.smjeon.commerce.coupon.converter.CouponConverter;
import dev.smjeon.commerce.coupon.dto.CouponCodeResponse;
import dev.smjeon.commerce.coupon.dto.CouponRequest;
import dev.smjeon.commerce.coupon.dto.CouponResponse;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponInternalService couponInternalService;

    public CouponService(CouponInternalService couponInternalService) {
        this.couponInternalService = couponInternalService;
    }

    public CouponResponse create(CouponRequest couponRequest) {
        return CouponConverter.toDto(couponInternalService.create(couponRequest));
    }

    public void expire(Long couponId) {
        couponInternalService.expire(couponId);
    }

    public CouponCodeResponse createRandomCode() {
        return new CouponCodeResponse(couponInternalService.createRandomCode());
    }
}
