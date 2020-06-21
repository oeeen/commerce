package dev.smjeon.commerce.product.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotViewableProductException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NotViewableProductException.class);
    private static final String MESSAGE = "조회할 수 없는 상품입니다.";

    public NotViewableProductException(String status) {
        super(MESSAGE);
        logger.debug(MESSAGE + "현재 상품 상태: {}", status);
    }
}
