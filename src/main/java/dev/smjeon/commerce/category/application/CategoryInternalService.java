package dev.smjeon.commerce.category.application;

import dev.smjeon.commerce.category.domain.CategoryName;
import dev.smjeon.commerce.category.domain.LowestCategory;
import dev.smjeon.commerce.category.domain.SubCategory;
import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.category.dto.CategoryRequest;
import dev.smjeon.commerce.category.exception.DuplicatedCategoryException;
import dev.smjeon.commerce.category.exception.NotFoundCategoryException;
import dev.smjeon.commerce.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        TopCategory requestedCategory = createTopCategory(categoryRequest);

        CategoryName topCategoryName = new CategoryName(categoryRequest.getTopCategory());
        Optional<TopCategory> topCategory = categoryRepository.findByName(topCategoryName).stream()
                .filter(category -> category.isSameCategory(requestedCategory))
                .findAny();

        if (topCategory.isPresent()) {
            throw new DuplicatedCategoryException(
                    requestedCategory.getCategoryNameValue() + " - " +
                            requestedCategory.getSubCategoryValue() + " - " +
                            requestedCategory.getLowestCategoryValue()
            );
        }

        return categoryRepository.save(requestedCategory);
    }

    private TopCategory createTopCategory(CategoryRequest categoryRequest) {
        LowestCategory lowestCategory = new LowestCategory(new CategoryName(categoryRequest.getLowestCategory()));
        SubCategory subCategory = new SubCategory(new CategoryName(categoryRequest.getSubCategory()), lowestCategory);

        return new TopCategory(new CategoryName(categoryRequest.getTopCategory()), subCategory);
    }
}
