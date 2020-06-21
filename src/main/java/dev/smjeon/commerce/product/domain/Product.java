package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.common.BaseEntity;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.exception.MismatchedUserException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
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

    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "owner_id"), nullable = false)
    private User owner;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductStatus status;

    public Product(TopCategory topCategory, ProductName name, ProductType type, Price price,
                   ShippingFee shippingFee, User owner, ProductStatus status) {
        this.topCategory = topCategory;
        this.name = name;
        this.type = type;
        this.price = price;
        this.shippingFee = shippingFee;
        this.owner = owner;
        this.status = status;
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

    public void update(User owner, ProductName name, ProductType type, Price price, ShippingFee shippingFee) {
        checkOwner(owner);
        this.name = name;
        this.type = type;
        this.price = price;
        this.shippingFee = shippingFee;
    }

    public void remove(User owner) {
        checkOwner(owner);
        this.status = ProductStatus.REMOVED;
    }

    private void checkOwner(User requestedOwner) {
        if (!this.owner.equals(requestedOwner)) {
            throw new MismatchedUserException(requestedOwner.getId());
        }
    }

    public void block() {
        this.status = ProductStatus.BLOCKED;
    }

    public boolean isViewable() {
        return ProductStatus.ACTIVE.equals(this.status);
    }
}
