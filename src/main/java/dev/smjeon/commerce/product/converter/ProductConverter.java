package dev.smjeon.commerce.product.converter;

import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.dto.ProductResponse;
import dev.smjeon.commerce.user.converter.UserConverter;
import dev.smjeon.commerce.user.domain.User;

public class ProductConverter {
    private ProductConverter() {
    }

    public static ProductResponse toDto(Product product) {
        User owner = product.getOwner();
        return new ProductResponse(
                product.getId(),
                product.getBrandNameValue(),
                product.getProductNameValue(),
                product.getTopCategoryValue(),
                product.getSubCategoryValue(),
                product.getLowestCategoryValue(),
                product.getPriceValue(),
                product.getShippingFeeValue(),
                UserConverter.toDto(owner)
        );
    }
}
