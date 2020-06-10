package dev.smjeon.commerce.category.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundCategoryException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundCategoryException.class);
    private static final String MESSAGE = "존재하지 않는 카테고리 입니다.";

    public NotFoundCategoryException(Long id) {
        super(MESSAGE);
        logger.debug(MESSAGE + " Requested CategoryId: {}", id);
    }
}
