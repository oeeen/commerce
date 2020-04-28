package dev.smjeon.commerce.product.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidShippingFeeException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(InvalidShippingFeeException.class);

    private static final String MESSAGE = "배송비는 0원부터 3만원까지 가능합니다.";

    public InvalidShippingFeeException(int value) {
        super(MESSAGE);
        logger.debug("배송비 입력이 잘못 되었습니다. 들어온 입력: {}", value);
    }
}
