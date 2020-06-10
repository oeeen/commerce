package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.converter.CategoryConverter;
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
                .map(CategoryConverter::toDto)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Long id) {
        return CategoryConverter.toDto(categoryInternalService.findById(id));
    }
}
