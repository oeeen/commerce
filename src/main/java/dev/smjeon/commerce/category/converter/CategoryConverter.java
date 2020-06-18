package dev.smjeon.commerce.category.converter;

import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.dto.CategoryResponse;

public class CategoryConverter {
    private CategoryConverter() {
    }

    public static CategoryResponse toDto(TopCategory topCategory) {
        return new CategoryResponse(
                topCategory.getId(),
                topCategory.getCategoryNameValue(),
                topCategory.getSubCategoryValue(),
                topCategory.getLowestCategoryValue()
        );
    }
}
