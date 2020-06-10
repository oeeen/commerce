package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.dto.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryInternalService categoryInternalService;

    public CategoryService(CategoryInternalService categoryInternalService) {
        this.categoryInternalService = categoryInternalService;
    }

    public List<CategoryResponse> findAll() {
        List<TopCategory> categories = categoryInternalService.findAll();

        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CategoryResponse toDto(TopCategory topCategory) {
        return new CategoryResponse(
                topCategory.getCategoryNameValue(),
                topCategory.getSubCategoryValue(),
                topCategory.getLowestCategoryValue()
        );
    }

    public CategoryResponse findById(Long id) {
        return toDto(categoryInternalService.findById(id));
    }
}
