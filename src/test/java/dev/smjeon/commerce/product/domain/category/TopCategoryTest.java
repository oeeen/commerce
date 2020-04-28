package dev.smjeon.commerce.product.domain.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TopCategoryTest {
    @Test
    @DisplayName("새로운 최상위 카테고리를 정상적으로 생성합니다.")
    void createTopCategory() {
        CategoryName topCategoryName = new CategoryName("식품");
        CategoryName subCategoryName = new CategoryName("신선식품");
        CategoryName lowestCategoryName = new CategoryName("쌀");
        LowestCategory lowestCategory = new LowestCategory(lowestCategoryName);
        SubCategory subCategory = new SubCategory(subCategoryName, lowestCategory);

        assertDoesNotThrow(() -> new TopCategory(topCategoryName, subCategory));
    }
}
