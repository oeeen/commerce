package dev.smjeon.commerce.user.exception;

import dev.smjeon.commerce.user.domain.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicatedEmailException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(DuplicatedEmailException.class);

    public DuplicatedEmailException(Email email) {
        super("중복된 이메일 입력입니다.");
        logger.debug("중복된 이메일 입력입니다. 들어온 입력: {}", email.getEmail());
    }
}
