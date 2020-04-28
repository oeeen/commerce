package dev.smjeon.commerce.product.domain.exception;

public class InvalidBrandNameException extends RuntimeException {
    private static final String MESSAGE = "브랜드 명은 특수문자가 불가능합니다.";

    public InvalidBrandNameException() {
        super(MESSAGE);
    }
}
