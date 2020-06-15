package dev.smjeon.commerce.user.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundUserException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundUserException.class);
    private static final String MESSAGE = "존재 하지 않는 사용자 입니다.";

    public NotFoundUserException(String userName) {
        super(MESSAGE);
        logger.debug(MESSAGE + "들어온 입력: {}", userName);
    }

    public NotFoundUserException(Long userId) {
        super(MESSAGE);
        logger.debug(MESSAGE + "들어온 ID: {}", userId);
    }
}
