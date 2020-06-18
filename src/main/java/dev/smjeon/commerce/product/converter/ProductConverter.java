package dev.smjeon.commerce.product.converter;

import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.dto.ProductResponse;

public class ProductConverter {
    private ProductConverter() {
    }

    public static ProductResponse toDto(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getBrandNameValue(),
                product.getProductNameValue(),
                product.getTopCategoryValue(),
                product.getSubCategoryValue(),
                product.getLowestCategoryValue(),
                product.getPriceValue(),
                product.getShippingFeeValue()
        );
    }
}
