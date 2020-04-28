package dev.smjeon.commerce.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ProductTest {

    @Test
    @DisplayName("카테고리, 상품 타입, 상품명, 기본 가격, 재고 수, 기본 배송비로 상품을 등록합니다.")
    void constructProduct() {
        assertDoesNotThrow(() -> new Product("식품", "일반", "사과", 10_000, 500));
    }
}
