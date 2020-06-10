package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.exception.NotFoundCategoryException;
import dev.smjeon.commerce.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryInternalService {
    private final CategoryRepository categoryRepository;

    public CategoryInternalService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<TopCategory> findAll() {
        return categoryRepository.findAll();
    }


    public TopCategory findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundCategoryException(id));
    }
}
