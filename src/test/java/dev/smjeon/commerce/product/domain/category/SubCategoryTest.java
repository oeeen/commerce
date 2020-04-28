package dev.smjeon.commerce.product.domain.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SubCategoryTest {
    @Test
    @DisplayName("새로운 중간단계 카테고리를 정상적으로 생성합니다.")
    void createTopCategory() {
        CategoryName subCategoryName = new CategoryName("신선식품");
        CategoryName lowestCategoryName = new CategoryName("쌀");
        LowestCategory lowestCategory = new LowestCategory(lowestCategoryName);

        assertDoesNotThrow(() -> new SubCategory(subCategoryName, lowestCategory));
    }
}
