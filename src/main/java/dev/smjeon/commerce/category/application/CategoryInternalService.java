package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.domain.CategoryName;
import dev.smjeon.commerce.category.domain.LowestCategory;
import dev.smjeon.commerce.category.domain.SubCategory;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.dto.CategoryRequest;
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

    public TopCategory create(CategoryRequest categoryRequest) {
        CategoryName topCategory = new CategoryName(categoryRequest.getTopCategory());
        TopCategory result = categoryRepository.findByName(topCategory)
                .filter((top) -> top.isSameCategory(createTopCategory(categoryRequest)))
                .orElseGet(() -> createTopCategory(categoryRequest));

        return categoryRepository.save(result);
    }

    private TopCategory createTopCategory(CategoryRequest categoryRequest) {
        LowestCategory lowestCategory = new LowestCategory(new CategoryName(categoryRequest.getLowestCategory()));
        SubCategory subCategory = new SubCategory(new CategoryName(categoryRequest.getSubCategory()), lowestCategory);

        return new TopCategory(new CategoryName(categoryRequest.getTopCategory()), subCategory);
    }
}
