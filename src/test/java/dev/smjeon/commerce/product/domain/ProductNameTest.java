package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.product.exception.InvalidBrandNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductNameTest {
    @Test
    @DisplayName("정상적으로 상품 이름이 생성됩니다.")
    void normalProductName() {
        assertDoesNotThrow(() -> new ProductName("농협", "파주참드림_10KG 포"));
    }

    @Test
    @DisplayName("브랜드 이름은 띄어쓰기는 가능합니다.")
    void normalProductNameWithWhitespace() {
        assertDoesNotThrow(() -> new ProductName("띄 어 쓰 기", "상품명"));
    }

    @Test
    @DisplayName("브랜드 이름은 특수문자가 불가능합니다.")
    void abNormalBrandName() {
        assertThrows(InvalidBrandNameException.class, () -> new ProductName("!@#$", "파주참드림_10KG 포"));
    }

    @Test
    @DisplayName("동일 브랜드의 동일 상품 이름은 동일 상품입니다.")
    void sameProduct() {
        ProductName productOne = new ProductName("농협", "파주참드림_10KG 포");
        ProductName productTwo = new ProductName("농협", "파주참드림_10KG 포");
        assertEquals(productOne, productTwo);
    }

    @Test
    @DisplayName("동일 브랜드의 다른 상품 이름은 다른 상품입니다.")
    void sameBrandDifferentProductName() {
        ProductName productOne = new ProductName("농협", "파주참드림_10KG 포");
        ProductName productTwo = new ProductName("농협", "파주참드림_20KG 포");
        assertNotEquals(productOne, productTwo);
    }
}
