package dev.smjeon.commerce.category.presentation;

import dev.smjeon.commerce.category.application.CategoryService;
import dev.smjeon.commerce.category.dto.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/categories")
@RestController
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
