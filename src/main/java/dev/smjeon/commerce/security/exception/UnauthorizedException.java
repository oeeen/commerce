package dev.smjeon.commerce.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnauthorizedException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(UnauthorizedException.class);
    private static final String MESSAGE = "인증 실패.";

    public UnauthorizedException() {
        super(MESSAGE);
        logger.debug("인증 실패");
    }
}
