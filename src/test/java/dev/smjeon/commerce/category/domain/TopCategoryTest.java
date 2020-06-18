package dev.smjeon.commerce.category.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    @DisplayName("같은 카테고리면 true return")
    void isSameCategory() {
        CategoryName topCategoryName = new CategoryName("식품");
        CategoryName subCategoryName = new CategoryName("신선식품");
        CategoryName lowestCategoryName = new CategoryName("쌀");
        LowestCategory lowestCategory = new LowestCategory(lowestCategoryName);
        SubCategory subCategory = new SubCategory(subCategoryName, lowestCategory);
        TopCategory topCategory = new TopCategory(topCategoryName, subCategory);

        assertTrue(topCategory.isSameCategory(new TopCategory(topCategoryName, subCategory)));
    }

    @Test
    @DisplayName("3가지 카테고리가 모두 일치하지 않으면 false return")
    void isSameCategoryWithDifferentCategory() {
        CategoryName topCategoryName = new CategoryName("식품");
        CategoryName subCategoryName = new CategoryName("신선식품");
        CategoryName lowestCategoryName = new CategoryName("쌀");
        LowestCategory lowestCategory = new LowestCategory(lowestCategoryName);
        SubCategory subCategory = new SubCategory(subCategoryName, lowestCategory);
        TopCategory topCategory = new TopCategory(topCategoryName, subCategory);
        SubCategory diffSubCategory = new SubCategory(subCategoryName, new LowestCategory(new CategoryName("다른 카테고리")));

        assertFalse(topCategory.isSameCategory(new TopCategory(topCategoryName, diffSubCategory)));
    }
}
