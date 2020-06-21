package dev.smjeon.commerce.product.application;

import dev.smjeon.commerce.category.application.CategoryInternalService;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.common.WithMockCustomUser;
import dev.smjeon.commerce.product.domain.Price;
import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.domain.ProductName;
import dev.smjeon.commerce.product.domain.ProductStatus;
import dev.smjeon.commerce.product.domain.ProductType;
import dev.smjeon.commerce.product.domain.ShippingFee;
import dev.smjeon.commerce.product.dto.ProductRequest;
import dev.smjeon.commerce.product.dto.ProductResponse;
import dev.smjeon.commerce.product.exception.NotViewableProductException;
import dev.smjeon.commerce.product.repository.ProductRepository;
import dev.smjeon.commerce.user.application.UserInternalService;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.domain.UserRole;
import dev.smjeon.commerce.user.domain.UserStatus;
import dev.smjeon.commerce.user.exception.MismatchedUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private UserInternalService userService;

    @Mock
    private CategoryInternalService categoryInternalService;

    @Mock
    private TopCategory category;

    @Mock
    private User owner;

    @Mock
    private User other;

    private User admin;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(category,
                new ProductName("testBrand", "testProduct"),
                ProductType.NORMAL,
                new Price(100_000),
                new ShippingFee(3_000),
                owner,
                ProductStatus.ACTIVE);

        admin = new User(new Email("admin@admin.com"),
                new Password("Aa12345!"),
                "Admin",
                new NickName("Admin"),
                UserRole.ADMIN,
                UserStatus.ACTIVE);
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

    @Test
    @WithMockCustomUser(role = UserRole.SELLER)
    @DisplayName("본인의 상품이면 수정이 됩니다.")
    void updateWithOwner() {
        ProductName productName = new ProductName("브랜드", "상품명");
        ProductRequest request = new ProductRequest(productName, ProductType.NORMAL, new Price(100_000), new ShippingFee(3_000));

        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));
        given(userService.findById(anyLong())).willReturn(owner);

        ProductResponse response = productService.update(1L, request);

        verify(productRepository).findById(1L);
        assertEquals(response.getBrandName(), "브랜드");
        assertEquals(response.getProductName(), "상품명");
        assertEquals(response.getPrice(), 100_000);
        assertEquals(response.getShippingFee(), 3_000);
    }

    @Test
    @WithMockCustomUser(role = UserRole.SELLER)
    @DisplayName("본인의 상품이 아니면 수정이 불가능합니다.")
    void updateWithoutOwner() {
        ProductName productName = new ProductName("브랜드", "상품명");
        ProductRequest request = new ProductRequest(productName, ProductType.NORMAL, new Price(100_000), new ShippingFee(3_000));

        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));
        given(userService.findById(anyLong())).willReturn(other);

        assertThrows(MismatchedUserException.class, () -> productService.update(1L, request));

        verify(productRepository).findById(1L);
    }

    @Test
    @WithMockCustomUser(role = UserRole.SELLER)
    @DisplayName("본인의 상품이면 삭제가 됩니다.(ProductStatus = REMOVED)")
    void deleteWithOwner() {
        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));
        given(userService.findById(anyLong())).willReturn(owner);

        productService.delete(1L);

        verify(productRepository).findById(1L);
        assertEquals(product.getStatus(), ProductStatus.REMOVED);
    }

    @Test
    @WithMockCustomUser(role = UserRole.ADMIN)
    @DisplayName("관리자가 삭제요청을 하면 Blocked 상태로 변경됩니다.")
    void deleteWithAdmin() {
        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));
        given(userService.findById(anyLong())).willReturn(admin);

        productService.delete(1L);

        verify(productRepository).findById(1L);
        assertEquals(product.getStatus(), ProductStatus.BLOCKED);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("상품 Id로 조회가 가능합니다.")
    void findById() {
        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));

        productService.findById(1L);

        verify(productRepository).findById(1L);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("REMOVED 상태의 상품을 조회 시도 시 NotViewableProductException이 발생합니다.")
    void findByIdRemovedProduct() {
        Product removed = new Product(
                category,
                new ProductName("삭제 브랜드", "삭제 상품"),
                ProductType.NORMAL,
                new Price(100_000),
                new ShippingFee(3_000),
                owner,
                ProductStatus.REMOVED
        );

        given(productRepository.findById(anyLong())).willReturn(Optional.of(removed));

        assertThrows(NotViewableProductException.class, () -> productService.findById(1L));
        verify(productRepository).findById(1L);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("BLOCKED 상태의 상품을 조회 시도 시 NotViewableProductException이 발생합니다.")
    void findByIdBLOCKEDProduct() {
        Product blocked = new Product(
                category,
                new ProductName("차단 브랜드", "차단 상품"),
                ProductType.NORMAL,
                new Price(100_000),
                new ShippingFee(3_000),
                owner,
                ProductStatus.BLOCKED
        );

        given(productRepository.findById(anyLong())).willReturn(Optional.of(blocked));

        assertThrows(NotViewableProductException.class, () -> productService.findById(1L));
        verify(productRepository).findById(1L);
    }
}