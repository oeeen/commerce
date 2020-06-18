package dev.smjeon.commerce.product.application;

import dev.smjeon.commerce.category.application.CategoryInternalService;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.product.converter.ProductConverter;
import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.domain.ProductType;
import dev.smjeon.commerce.product.dto.ProductRequest;
import dev.smjeon.commerce.product.dto.ProductResponse;
import dev.smjeon.commerce.product.exception.NotFoundProductException;
import dev.smjeon.commerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ProductResponse> findAllByProductType(ProductType type) {
        List<Product> products = productRepository.findAllByType(type);

        return products.stream()
                .map(ProductConverter::toDto)
                .collect(Collectors.toList());
    }

    public ProductResponse create(ProductRequest productRequest, Long categoryId) {
        TopCategory category = categoryInternalService.findById(categoryId);
        Product product = new Product(category, productRequest.getName(), productRequest.getType(), productRequest.getPrice(), productRequest.getShippingFee());

        return ProductConverter.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundProductException(productId));
        product.update(productRequest.getName(),
                productRequest.getType(),
                productRequest.getPrice(),
                productRequest.getShippingFee());

        return ProductConverter.toDto(product);
    }
}
