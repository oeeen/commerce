package dev.smjeon.commerce.product.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidPriceException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(InvalidPriceException.class);

    private static final String MESSAGE = "불가능한 가격입니다.";

    public InvalidPriceException(int value) {
        super(MESSAGE);
        logger.debug("가격 입력이 잘못되었습니다. 들어온 입력: {}", value);
    }
}
