package dev.smjeon.commerce.product.domain;

public class Product {
    private final TopCategory topCategory;
    private final ProductName name;
    private final ProductType type;
    private final Price price;
    private final ShippingFee shippingFee;

    public Product(TopCategory topCategory, ProductName name, ProductType type, Price price, ShippingFee shippingFee) {
        this.topCategory = topCategory;
        this.name = name;
        this.type = type;
        this.price = price;
        this.shippingFee = shippingFee;
    }
}
