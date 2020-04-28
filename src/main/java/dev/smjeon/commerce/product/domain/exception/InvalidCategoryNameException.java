package dev.smjeon.commerce.product.domain.exception;

public class InvalidCategoryNameException extends RuntimeException {
    private static final String MESSAGE = "카테고리 이름에 특수문자는 불가능합니다.";

    public InvalidCategoryNameException() {
        super(MESSAGE);
    }
}
