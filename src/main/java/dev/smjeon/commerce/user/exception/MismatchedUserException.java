package dev.smjeon.commerce.user.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

public class MismatchedUserException extends AccessDeniedException {
    private static final Logger logger = LoggerFactory.getLogger(MismatchedUserException.class);
    private static final String MESSAGE = "일치하지 않는 사용자입니다.";

    public MismatchedUserException(Long userId) {
        super(MESSAGE);
        logger.debug(MESSAGE + " 들어온 요청: {}", userId);
    }
}
