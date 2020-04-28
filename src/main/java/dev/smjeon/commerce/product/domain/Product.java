package dev.smjeon.commerce.product.domain;

public class Product {
    private final TopCategory topCategory;
    private final ProductName name;
    private final ProductType type;
    private final int price;
    private final int shipAmount;

    public Product(TopCategory topCategory, ProductName name, ProductType type, int price, int shipAmount) {
        this.topCategory = topCategory;
        this.name = name;
        this.type = type;
        this.price = price;
        this.shipAmount = shipAmount;
    }
}
