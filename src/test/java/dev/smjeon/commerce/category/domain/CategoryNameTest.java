package dev.smjeon.commerce.category.domain;

import dev.smjeon.commerce.category.exception.InvalidCategoryNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoryNameTest {
    @Test
    @DisplayName("정상적으로 카테고리 이름이 생성 됩니다.")
    void normalCategory() {
        assertDoesNotThrow(() -> new CategoryName("쌀"));
    }

    @Test
    @DisplayName("카테고리 이름에 공백은 가능합니다.")
    void categoryNameWithWhiteSpace() {
        assertDoesNotThrow(() -> new CategoryName("최상위 카테고리"));
    }

    @Test
    @DisplayName("카테고리 이름에는 특수문자가 불가능합니다.")
    void abnormalCategory() {
        assertThrows(InvalidCategoryNameException.class, () -> new CategoryName("!@#$"));
    }

    @Test
    @DisplayName("동일한 카테고리 이름은 동일한 객체입니다.")
    void sameCategoryName() {
        CategoryName first = new CategoryName("쌀");
        CategoryName second = new CategoryName("쌀");
        assertEquals(first, second);
    }
}
