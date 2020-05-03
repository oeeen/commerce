package dev.smjeon.commerce.user.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidEmailException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(InvalidEmailException.class);

    private static final String MESSAGE = "부적합한 이메일 입니다.";

    public InvalidEmailException(String value) {
        super(MESSAGE);
        logger.debug("부적합한 이메일 입니다. 들어온 입력: {}", value);
    }
}
