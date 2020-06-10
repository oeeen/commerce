package dev.smjeon.commerce.product.application;

import dev.smjeon.commerce.category.application.CategoryInternalService;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.domain.ProductType;
import dev.smjeon.commerce.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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

    @Test
    void findAll() {
        given(productRepository.findAll()).willReturn(Collections.singletonList(mock(Product.class)));

        productService.findAll();

        verify(productRepository).findAll();
    }

    @Test
    void findByCategory() {
        given(categoryInternalService.findById(anyLong())).willReturn(category);
        given(productRepository.findAllByTopCategory(any(TopCategory.class))).willReturn(Collections.singletonList(mock(Product.class)));

        productService.findByCategory(10L);

        verify(productRepository).findAllByTopCategory(category);
        verify(categoryInternalService).findById(anyLong());
    }

    @Test
    void findAllEventProduct() {
        given(productRepository.findAllByType(any(ProductType.class))).willReturn(Collections.singletonList(mock(Product.class)));

        productService.findAllByProductType(ProductType.EVENT);

        verify(productRepository).findAllByType(ProductType.EVENT);
        verify(productRepository, times(0)).findAllByType(ProductType.NORMAL);
    }
}