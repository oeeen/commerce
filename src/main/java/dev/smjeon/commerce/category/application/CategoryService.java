package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.domain.LowestCategory;
import dev.smjeon.commerce.category.domain.SubCategory;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.dto.CategoryResponse;
import dev.smjeon.commerce.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> findAll() {
        List<TopCategory> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> {
                    SubCategory subCategory = category.getSubCategory();
                    LowestCategory lowestCategory = subCategory.getLowestCategory();
                    return new CategoryResponse(
                            category.getName().getValue(),
                            subCategory.getName().getValue(),
                            lowestCategory.getName().getValue()
                    );
                })
                .collect(Collectors.toList());
    }
}
