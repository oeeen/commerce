package dev.smjeon.commerce.product.domain.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LowestCategoryTest {
    @Test
    @DisplayName("새로운 최하위 카테고리를 정상적으로 생성합니다.")
    void createTopCategory() {
        CategoryName lowestCategoryName = new CategoryName("쌀");

        assertDoesNotThrow(() -> new LowestCategory(lowestCategoryName));
    }
}
