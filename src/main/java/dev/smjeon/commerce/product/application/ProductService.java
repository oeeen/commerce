package dev.smjeon.commerce.product.application;

import dev.smjeon.commerce.category.application.CategoryInternalService;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.product.converter.ProductConverter;
import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.domain.ProductType;
import dev.smjeon.commerce.product.dto.ProductResponse;
import dev.smjeon.commerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryInternalService categoryInternalService;

    public ProductService(ProductRepository productRepository, CategoryInternalService categoryInternalService) {
        this.productRepository = productRepository;
        this.categoryInternalService = categoryInternalService;
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategory(Long categoryId) {
        TopCategory category = categoryInternalService.findById(categoryId);
        List<Product> products = productRepository.findAllByTopCategory(category);

        return products.stream()
                .map(ProductConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findAllEventProducts() {
        List<Product> products = productRepository.findAllByType(ProductType.EVENT);

        return products.stream()
                .map(ProductConverter::toDto)
                .collect(Collectors.toList());
    }
}
