package dev.smjeon.commerce.product.domain;

public class Product {
    private final TopCategory topCategory;
    private final String name;
    private final String type;
    private final int price;
    private final int shipAmount;

    public Product(TopCategory topCategory, String type, String name, int price, int shipAmount) {
        this.topCategory = topCategory;
        this.type = type;
        this.name = name;
        this.price = price;
        this.shipAmount = shipAmount;
    }
}
