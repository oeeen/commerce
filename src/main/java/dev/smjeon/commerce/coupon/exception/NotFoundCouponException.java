package dev.smjeon.commerce.coupon.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

public class NotFoundCouponException extends AccessDeniedException {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundCouponException.class);

    public static final String MESSAGE = "존재하지 않는 쿠폰입니다.";

    public NotFoundCouponException(Long couponId) {
        super(MESSAGE);
        logger.debug(MESSAGE + "들어온 couponId: {}", couponId);
    }
}
