package dev.smjeon.commerce.category.presentation;

import dev.smjeon.commerce.category.application.CategoryService;
import dev.smjeon.commerce.category.dto.CategoryRequest;
import dev.smjeon.commerce.category.dto.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse response = categoryService.create(categoryRequest);
        return ResponseEntity.created(URI.create("/api/categories/" + response.getId())).body(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long categoryId,
                                                   @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.update(categoryId, categoryRequest));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);

        return ResponseEntity.noContent().build();
    }
}
