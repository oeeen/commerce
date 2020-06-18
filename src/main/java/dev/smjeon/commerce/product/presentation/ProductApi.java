package dev.smjeon.commerce.product.presentation;

import dev.smjeon.commerce.product.application.ProductService;
import dev.smjeon.commerce.product.domain.ProductType;
import dev.smjeon.commerce.product.dto.ProductRequest;
import dev.smjeon.commerce.product.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/products")
@RestController
public class ProductApi {

    private final ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategory(categoryId));
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<ProductResponse>> findAllByProductType(@PathVariable ProductType type) {
        return ResponseEntity.ok(productService.findAllByProductType(type));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestParam(value = "category") Long categoryId,
                                                         @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.create(productRequest, categoryId);
        return ResponseEntity.created(URI.create("/api/categories/" + categoryId + "/products/" + response.getId())).body(response);
    }
}
