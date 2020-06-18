package dev.smjeon.commerce.product.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundProductException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundProductException.class);
    private static final String MESSAGE = "존재하지 않는 상품입니다.";

    public NotFoundProductException(Long productId) {
        super(MESSAGE);
        logger.debug(MESSAGE + "들어온 상품 ID: {}", productId);
    }
}
