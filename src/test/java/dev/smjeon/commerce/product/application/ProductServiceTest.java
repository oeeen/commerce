package dev.smjeon.commerce.product.application;

import dev.smjeon.commerce.category.application.CategoryInternalService;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.product.domain.Price;
import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.domain.ProductName;
import dev.smjeon.commerce.product.domain.ProductType;
import dev.smjeon.commerce.product.domain.ShippingFee;
import dev.smjeon.commerce.product.repository.ProductRepository;
import dev.smjeon.commerce.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryInternalService categoryInternalService;

    @Mock
    private TopCategory category;

    @Mock
    private User owner;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(category,
                new ProductName("testBrand", "testProduct"),
                ProductType.NORMAL,
                new Price(100_000),
                new ShippingFee(3_000),
                owner);
    }

    @Test
    void findAll() {
        List<Product> products = Collections.singletonList(product);
        given(productRepository.findAll()).willReturn(products);

        productService.findAll();

        verify(productRepository).findAll();
    }

    @Test
    void findByCategory() {
        List<Product> products = Collections.singletonList(product);

        given(categoryInternalService.findById(anyLong())).willReturn(category);
        given(productRepository.findAllByTopCategory(any(TopCategory.class))).willReturn(products);

        productService.findByCategory(10L);

        verify(productRepository).findAllByTopCategory(category);
        verify(categoryInternalService).findById(anyLong());
    }

    @Test
    void findAllEventProduct() {
        List<Product> products = Collections.singletonList(product);
        given(productRepository.findAllByType(any(ProductType.class))).willReturn(products);

        productService.findAllByProductType(ProductType.EVENT);

        verify(productRepository).findAllByType(ProductType.EVENT);
        verify(productRepository, times(0)).findAllByType(ProductType.NORMAL);
    }
}