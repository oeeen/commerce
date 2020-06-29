package dev.smjeon.commerce.coupon.application;

import dev.smjeon.commerce.coupon.domain.Coupon;
import dev.smjeon.commerce.coupon.domain.CouponCode;
import dev.smjeon.commerce.coupon.domain.CouponStatus;
import dev.smjeon.commerce.coupon.dto.CouponRequest;
import dev.smjeon.commerce.coupon.exception.DuplicatedCouponException;
import dev.smjeon.commerce.coupon.exception.NotFoundCouponException;
import dev.smjeon.commerce.coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CouponInternalService {

    private final CouponRepository couponRepository;

    public CouponInternalService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon create(CouponRequest couponRequest) {
        CouponCode code = new CouponCode(couponRequest.getCode());
        if (couponRepository.findByCode(code).isPresent()) {
            throw new DuplicatedCouponException(code.getValue());
        }

        Coupon coupon = new Coupon(
                couponRequest.getCouponName(),
                new CouponCode(couponRequest.getCode()),
                couponRequest.getType(),
                couponRequest.getRate(),
                CouponStatus.NORMAL
        );

        return couponRepository.save(coupon);
    }

    public void expire(Long couponId) {
        Coupon requestedCoupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundCouponException(couponId));
        requestedCoupon.expire();
    }
}
