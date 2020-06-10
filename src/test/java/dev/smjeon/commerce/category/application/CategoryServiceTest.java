package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.domain.CategoryName;
import dev.smjeon.commerce.category.domain.LowestCategory;
import dev.smjeon.commerce.category.domain.SubCategory;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.dto.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryInternalService categoryInternalService;

    @Test
    @DisplayName("조회 시 카테고리 이름들이 담긴 리스트가 나옵니다.")
    void findAll() {
        LowestCategory lowestCategory = new LowestCategory(new CategoryName("최하위카테고리"));
        SubCategory subCategory = new SubCategory(new CategoryName("중간카테고리"), lowestCategory);
        TopCategory category = new TopCategory(new CategoryName("최상위카테고리"), subCategory);
        List<TopCategory> categories = Collections.singletonList(category);
        given(categoryInternalService.findAll()).willReturn(categories);

        List<CategoryResponse> categoryResponses = categoryService.findAll();
        CategoryResponse response = categoryResponses.get(0);

        assertEquals(response.getTopCategory(), "최상위카테고리");
        assertEquals(response.getSubCategory(), "중간카테고리");
        assertEquals(response.getLowestCategory(), "최하위카테고리");
    }
}