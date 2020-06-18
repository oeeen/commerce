package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
@Entity
public class Product extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "category_id"))
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

    public String getTopCategoryValue() {
        return this.topCategory.getCategoryNameValue();
    }

    public String getSubCategoryValue() {
        return this.topCategory.getSubCategoryValue();
    }

    public String getLowestCategoryValue() {
        return this.topCategory.getLowestCategoryValue();
    }

    public String getBrandNameValue() {
        return this.name.getBrandName();
    }

    public String getProductNameValue() {
        return this.name.getProductName();
    }

    public int getPriceValue() {
        return this.price.getValue();
    }

    public int getShippingFeeValue() {
        return this.shippingFee.getValue();
    }

    public boolean isEventType() {
        return ProductType.EVENT.equals(this.type);
    }

    public void update(ProductName name, ProductType type, Price price, ShippingFee shippingFee) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.shippingFee = shippingFee;
    }
}
