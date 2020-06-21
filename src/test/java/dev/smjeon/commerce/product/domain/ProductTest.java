package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.category.domain.CategoryName;
import dev.smjeon.commerce.category.domain.LowestCategory;
import dev.smjeon.commerce.category.domain.SubCategory;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.domain.UserRole;
import dev.smjeon.commerce.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {
    private TopCategory topCategory;
    private ProductName productName;
    private ProductType productType;
    private Price price;
    private ShippingFee shippingFee;
    private User owner;

    @BeforeEach
    void setUp() {
        owner = new User(
                new Email("oeeen3@gmail.com"),
                new Password("Aa12345!"),
                "Seongmo",
                new NickName("Martin"),
                UserRole.SELLER,
                UserStatus.ACTIVE);
        CategoryName topCategoryName = new CategoryName("식품");
        CategoryName subCategoryName = new CategoryName("신선식품");
        CategoryName lowestCategoryName = new CategoryName("쌀");
        LowestCategory lowestCategory = new LowestCategory(lowestCategoryName);
        SubCategory subCategory = new SubCategory(subCategoryName, lowestCategory);
        topCategory = new TopCategory(topCategoryName, subCategory);
        productName = new ProductName("농협", "파주참드림_10KG 포");
        productType = ProductType.NORMAL;
        price = new Price(10_000);
        shippingFee = new ShippingFee(2_500);
    }

    @Test
    @DisplayName("카테고리, 상품 타입, 상품명, 기본 가격, 재고 수, 기본 배송비, 등록자로 상품을 등록합니다.")
    void constructProduct() {
        assertDoesNotThrow(() ->
                new Product(
                        topCategory,
                        productName,
                        productType,
                        price,
                        shippingFee,
                        owner,
                        ProductStatus.ACTIVE));
    }

    @Test
    @DisplayName("동일 유저일 경우 ProductStatus가 REMOVED로 변경됩니다.")
    void remove() {
        Product product = new Product(
                topCategory,
                productName,
                productType,
                price,
                shippingFee,
                owner,
                ProductStatus.ACTIVE
        );

        product.remove(owner);

        assertEquals(product.getStatus(), ProductStatus.REMOVED);
    }

    @Test
    @DisplayName("ProductStatus가 BLOCKED로 변경됩니다.")
    void removeByAdmin() {
        Product product = new Product(
                topCategory,
                productName,
                productType,
                price,
                shippingFee,
                owner,
                ProductStatus.ACTIVE
        );

        product.block();

        assertEquals(product.getStatus(), ProductStatus.BLOCKED);
    }

    @Test
    @DisplayName("ProductStatus가 ACTIVE가 아니면 false")
    void isViewable() {
        Product product = new Product(
                topCategory,
                productName,
                productType,
                price,
                shippingFee,
                owner,
                ProductStatus.ACTIVE
        );

        assertTrue((product.isViewable()));

        product.remove(owner);
        assertFalse(product.isViewable());

        product.block();
        assertFalse(product.isViewable());
    }
}
