package dev.smjeon.commerce.product.application;

import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.dto.ProductResponse;
import dev.smjeon.commerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProductResponse toDto(Product product) {
        return new ProductResponse(product.getBrandNameValue(),
                product.getProductNameValue(),
                product.getTopCategoryValue(),
                product.getSubCategoryValue(),
                product.getLowestCategoryValue(),
                product.getPriceValue(),
                product.getShippingFeeValue()
        );
    }
}
