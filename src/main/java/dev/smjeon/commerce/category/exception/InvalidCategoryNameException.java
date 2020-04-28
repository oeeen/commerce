package dev.smjeon.commerce.category.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidCategoryNameException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(InvalidCategoryNameException.class);

    private static final String MESSAGE = "카테고리 이름에 특수문자는 불가능합니다.";

    public InvalidCategoryNameException(String value) {
        super(MESSAGE);
        logger.debug("카테고리 이름에 특수문자는 불가능합니다. 들어온 입력: {}", value);
    }
}
