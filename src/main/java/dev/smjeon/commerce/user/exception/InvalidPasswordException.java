package dev.smjeon.commerce.user.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidPasswordException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(InvalidPasswordException.class);
    private static final String MESSAGE = "부적절한 비밀번호 입니다.";

    public InvalidPasswordException(String value) {
        super(MESSAGE);
        logger.debug("부적절한 비밀번호 입니다. 들어온 입력: {}", value);
    }
}
