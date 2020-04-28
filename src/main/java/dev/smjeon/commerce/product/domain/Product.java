package dev.smjeon.commerce.product.domain;

public class Product {
    private final String category;
    private final String name;
    private final String type;
    private final int price;
    private final int shipAmount;

    public Product(String category, String type, String name, int price, int shipAmount) {
        this.category = category;
        this.type = type;
        this.name = name;
        this.price = price;
        this.shipAmount = shipAmount;
    }
}
