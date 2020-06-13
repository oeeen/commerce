package dev.smjeon.commerce.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public class UnauthorizedException extends BadCredentialsException {
    private static final Logger logger = LoggerFactory.getLogger(UnauthorizedException.class);
    private static final String MESSAGE = "인증 실패.";

    public UnauthorizedException() {
        super(MESSAGE);
        logger.debug(MESSAGE);
    }
}
