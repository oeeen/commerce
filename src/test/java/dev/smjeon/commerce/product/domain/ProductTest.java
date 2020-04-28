package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.category.domain.CategoryName;
import dev.smjeon.commerce.category.domain.LowestCategory;
import dev.smjeon.commerce.category.domain.SubCategory;
import dev.smjeon.commerce.category.domain.TopCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ProductTest {

    @Test
    @DisplayName("카테고리, 상품 타입, 상품명, 기본 가격, 재고 수, 기본 배송비로 상품을 등록합니다.")
    void constructProduct() {
        CategoryName topCategoryName = new CategoryName("식품");
        CategoryName subCategoryName = new CategoryName("신선식품");
        CategoryName lowestCategoryName = new CategoryName("쌀");
        LowestCategory lowestCategory = new LowestCategory(lowestCategoryName);
        SubCategory subCategory = new SubCategory(subCategoryName, lowestCategory);

        ProductName productName = new ProductName("농협", "파주참드림_10KG 포");

        ProductType productType = ProductType.NORMAL;

        Price price = new Price(10_000);

        ShippingFee shippingFee = new ShippingFee(2_500);

        assertDoesNotThrow(() ->
                new Product(
                        new TopCategory(topCategoryName, subCategory),
                        productName,
                        productType,
                        price,
                        shippingFee));
    }
}
