package dev.smjeon.commerce.product.application;

import dev.smjeon.commerce.category.application.CategoryInternalService;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.product.converter.ProductConverter;
import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.domain.ProductStatus;
import dev.smjeon.commerce.product.domain.ProductType;
import dev.smjeon.commerce.product.dto.ProductRequest;
import dev.smjeon.commerce.product.dto.ProductResponse;
import dev.smjeon.commerce.product.exception.NotFoundProductException;
import dev.smjeon.commerce.product.repository.ProductRepository;
import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.user.application.UserInternalService;
import dev.smjeon.commerce.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryInternalService categoryInternalService;
    private final UserInternalService userService;

    public ProductService(ProductRepository productRepository, CategoryInternalService categoryInternalService, UserInternalService userService) {
        this.productRepository = productRepository;
        this.categoryInternalService = categoryInternalService;
        this.userService = userService;
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
        User foundUser = getUserFromAuthentication();

        Product product = new Product(category, productRequest.getName(), productRequest.getType(),
                productRequest.getPrice(), productRequest.getShippingFee(), foundUser, ProductStatus.ACTIVE);

        return ProductConverter.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundProductException(productId));
        User owner = getUserFromAuthentication();

        product.update(owner,
                productRequest.getName(),
                productRequest.getType(),
                productRequest.getPrice(),
                productRequest.getShippingFee());

        return ProductConverter.toDto(product);
    }

    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundProductException(productId));
        User owner = getUserFromAuthentication();

        product.remove(owner);
    }

    private User getUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserContext user = (UserContext) authentication.getPrincipal();
        return userService.findById(user.getId());
    }
}
