package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.domain.CategoryName;
import dev.smjeon.commerce.category.domain.LowestCategory;
import dev.smjeon.commerce.category.domain.SubCategory;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.dto.CategoryRequest;
import dev.smjeon.commerce.category.exception.DuplicatedCategoryException;
import dev.smjeon.commerce.category.exception.NotFoundCategoryException;
import dev.smjeon.commerce.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private CategoryRequest request;

    private LowestCategory lowestCategory;

    private SubCategory subCategory;

    private TopCategory topCategory;

    private List<TopCategory> categories;

    @BeforeEach
    void setUp() {
        request = new CategoryRequest("최상위", "중간", "최하위");
        lowestCategory = new LowestCategory(new CategoryName(request.getLowestCategory()));
        subCategory = new SubCategory(new CategoryName(request.getSubCategory()), lowestCategory);
        topCategory = new TopCategory(new CategoryName(request.getTopCategory()), subCategory);
        categories = Collections.singletonList(topCategory);
    }

    @Test
    @DisplayName("중복 카테고리 추가 시 DuplicatedCategoryException 발생")
    void createDuplicatedCategory() {
        given(categoryRepository.findByName(new CategoryName(request.getTopCategory()))).willReturn(categories);
        given(categoryRepository.save(any(TopCategory.class))).willReturn(topCategory);

        assertThrows(DuplicatedCategoryException.class, () -> categoryInternalService.create(request));

        verify(categoryRepository).findByName(new CategoryName(request.getTopCategory()));
        verify(categoryRepository, times(0)).save(topCategory);
    }

    @Test
    @DisplayName("카테고리 추가 성공")
    void createCategory() {
        given(categoryRepository.findByName(new CategoryName(request.getTopCategory()))).willReturn(Collections.emptyList());
        given(categoryRepository.save(any(TopCategory.class))).willReturn(topCategory);

        categoryInternalService.create(request);

        verify(categoryRepository).findByName(new CategoryName(request.getTopCategory()));
        verify(categoryRepository).save(topCategory);
    }

    @Test
    @DisplayName("카테고리 수정 성공")
    void updateCategory() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(topCategory));
        given(categoryRepository.findByName(new CategoryName(request.getTopCategory()))).willReturn(Collections.emptyList());

        TopCategory response = categoryInternalService.update(1L, request);

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).findByName(new CategoryName(request.getTopCategory()));
        assertEquals(response.getCategoryNameValue(), request.getTopCategory());
        assertEquals(response.getSubCategoryValue(), request.getSubCategory());
        assertEquals(response.getLowestCategoryValue(), request.getLowestCategory());
    }

    @Test
    @DisplayName("중복 카테고리로 수정시 DuplicatedCategoryException 발생")
    void updateDuplicatedCategory() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(topCategory));
        given(categoryRepository.findByName(new CategoryName(request.getTopCategory()))).willReturn(categories);

        assertThrows(DuplicatedCategoryException.class, () -> categoryInternalService.update(1L, request));

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).findByName(new CategoryName(request.getTopCategory()));
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 수정 시 NotFoundCategoryException")
    void updateNotFoundCategory() {
        given(categoryRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundCategoryException.class, () -> categoryInternalService.update(1L, request));

        verify(categoryRepository).findById(1L);
        verify(categoryRepository, times(0)).findByName(new CategoryName(request.getTopCategory()));
    }
}