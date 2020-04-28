package dev.smjeon.commerce.product.domain;

public class TopCategory {
    private final CategoryName name;
    private final SubCategory subCategory;

    public TopCategory(CategoryName name, SubCategory subCategory) {
        this.name = name;
        this.subCategory = subCategory;
    }
}
