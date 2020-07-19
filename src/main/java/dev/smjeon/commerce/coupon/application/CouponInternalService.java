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

import java.util.Random;

@Transactional
@Service
public class CouponInternalService {

    private static final int ALPHABET_COUNT = 26;
    private static final int NUMBER_COUNT = 10;
    private static final char NUMBER_OFFSET = '0';
    private static final char ALPHABET_OFFSET = 'A';
    private static final int CODE_LENGTH = 15;

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

    public String createRandomCode() {
        String code = createRandomCodeInternal();
        if (couponRepository.findByCode(new CouponCode(code)).isPresent()) {
            return createRandomCode();
        }

        return code;
    }

    private String createRandomCodeInternal() {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < CODE_LENGTH; i++) {
            stringBuffer.append(createRandomCharacter());
        }
        return stringBuffer.toString();
    }

    private char createRandomCharacter() {
        Random random = new Random();

        if (random.nextBoolean()) {
            return (char) (random.nextInt(ALPHABET_COUNT) + ALPHABET_OFFSET);
        }

        return (char) (random.nextInt(NUMBER_COUNT) + NUMBER_OFFSET);
    }
}
