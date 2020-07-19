package dev.smjeon.commerce.coupon.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CannotCreateCouponCodeException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(CannotCreateCouponCodeException.class);
    private static final String MESSAGE = "쿠폰 코드는 15자만 허용됩니다.";

    public CannotCreateCouponCodeException(String code) {
        super(MESSAGE);
        logger.debug(MESSAGE + "입력한 코드: {}", code);
    }
}
