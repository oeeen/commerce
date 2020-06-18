package dev.smjeon.commerce.product.dto;

import dev.smjeon.commerce.product.domain.Price;
import dev.smjeon.commerce.product.domain.ProductName;
import dev.smjeon.commerce.product.domain.ProductType;
import dev.smjeon.commerce.product.domain.ShippingFee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductRequest {
    private ProductName name;
    private ProductType type;
    private Price price;
    private ShippingFee shippingFee;

    public ProductRequest(ProductName name, ProductType type, Price price, ShippingFee shippingFee) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.shippingFee = shippingFee;
    }
}
