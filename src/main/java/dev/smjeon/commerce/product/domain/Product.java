package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Getter
@Entity
public class Product extends BaseEntity {

    @Embedded
    private TopCategory topCategory;

    @Embedded
    private ProductName name;

    @Column(nullable = false)
    @Enumerated
    private ProductType type;

    @Embedded
    private Price price;

    @Embedded
    private ShippingFee shippingFee;

    public Product(TopCategory topCategory, ProductName name, ProductType type, Price price, ShippingFee shippingFee) {
        this.topCategory = topCategory;
        this.name = name;
        this.type = type;
        this.price = price;
        this.shippingFee = shippingFee;
    }
}
