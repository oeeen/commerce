package dev.smjeon.commerce.product.domain;

public class SubCategory {
    private final CategoryName name;
    private final LowestCategory lowestCategory;

    public SubCategory(CategoryName name, LowestCategory lowestCategory) {
        this.name = name;
        this.lowestCategory = lowestCategory;
    }
}
