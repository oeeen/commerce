package dev.smjeon.commerce.category.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CategoryRequest {
    private String topCategory;
    private String subCategory;
    private String lowestCategory;

    public CategoryRequest(String topCategory, String subCategory, String lowestCategory) {
        this.topCategory = topCategory;
        this.subCategory = subCategory;
        this.lowestCategory = lowestCategory;
    }
}
