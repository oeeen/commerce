package dev.smjeon.commerce.category.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

public class DuplicatedCategoryException extends AccessDeniedException {
    private static final Logger logger = LoggerFactory.getLogger(DuplicatedCategoryException.class);

    public DuplicatedCategoryException(String requestedCategory) {
        super(requestedCategory);
        logger.debug("중복된 카테고리입니다. 들어온 요청: {}", requestedCategory);
    }
}
