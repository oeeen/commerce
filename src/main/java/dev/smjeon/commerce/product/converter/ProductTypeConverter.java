package dev.smjeon.commerce.product.converter;

import dev.smjeon.commerce.product.domain.ProductType;
import org.springframework.core.convert.converter.Converter;

public class ProductTypeConverter implements Converter<String, ProductType> {

    @Override
    public ProductType convert(String source) {
        return ProductType.valueOf(source.toUpperCase());
    }
}
