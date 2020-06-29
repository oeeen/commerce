package dev.smjeon.commerce.coupon.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;


public class DuplicatedCouponException extends AccessDeniedException {
    private static final Logger logger = LoggerFactory.getLogger(DuplicatedCouponException.class);
    private static final String MESSAGE = "중복된 쿠폰 코드입니다. 새로운 코드를 입력해주세요.";

    public DuplicatedCouponException(String code) {
        super(MESSAGE);
        logger.debug(MESSAGE + "입력된 코드: {}", code);
    }
}
