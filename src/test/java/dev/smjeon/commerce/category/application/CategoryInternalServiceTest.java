package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.domain.CategoryName;
import dev.smjeon.commerce.category.domain.LowestCategory;
import dev.smjeon.commerce.category.domain.SubCategory;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.dto.CategoryRequest;
import dev.smjeon.commerce.category.exception.DuplicatedCategoryException;
import dev.smjeon.commerce.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class CategoryInternalServiceTest {

    @InjectMocks
    private CategoryInternalService categoryInternalService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("중복 카테고리 추가 시 DuplicatedCategoryException 발생")
    void createDuplicatedCategory() {
        CategoryRequest request = new CategoryRequest("최상위", "중간", "최하위");
        LowestCategory lowestCategory = new LowestCategory(new CategoryName(request.getLowestCategory()));
        SubCategory subCategory = new SubCategory(new CategoryName(request.getSubCategory()), lowestCategory);
        TopCategory topCategory = new TopCategory(new CategoryName(request.getTopCategory()), subCategory);
        List<TopCategory> categories = Collections.singletonList(topCategory);

        given(categoryRepository.findByName(new CategoryName(request.getTopCategory()))).willReturn(categories);
        given(categoryRepository.save(any(TopCategory.class))).willReturn(topCategory);

        assertThrows(DuplicatedCategoryException.class, () -> categoryInternalService.create(request));

        verify(categoryRepository).findByName(new CategoryName(request.getTopCategory()));
        verify(categoryRepository, times(0)).save(topCategory);
    }

    @Test
    @DisplayName("카테고리 추가 성공")
    void createCategory() {
        CategoryRequest request = new CategoryRequest("최상위", "중간", "최하위");
        LowestCategory lowestCategory = new LowestCategory(new CategoryName(request.getLowestCategory()));
        SubCategory subCategory = new SubCategory(new CategoryName(request.getSubCategory()), lowestCategory);
        TopCategory topCategory = new TopCategory(new CategoryName(request.getTopCategory()), subCategory);

        given(categoryRepository.findByName(new CategoryName(request.getTopCategory()))).willReturn(Collections.emptyList());
        given(categoryRepository.save(any(TopCategory.class))).willReturn(topCategory);

        categoryInternalService.create(request);

        verify(categoryRepository).findByName(new CategoryName(request.getTopCategory()));
        verify(categoryRepository).save(topCategory);
    }
}