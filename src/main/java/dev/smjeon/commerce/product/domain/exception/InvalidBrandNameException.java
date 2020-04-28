package dev.smjeon.commerce.product.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidBrandNameException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(InvalidBrandNameException.class);

    private static final String MESSAGE = "브랜드 명은 특수문자가 불가능합니다.";

    public InvalidBrandNameException(String value) {
        super(MESSAGE);
        logger.debug("브랜드 명에 특수문자는 불가능합니다. 들어온 입력: {}", value);

    }
}
