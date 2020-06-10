package dev.smjeon.commerce.product.repository;

import dev.smjeon.commerce.category.domain.TopCategory;
import dev.smjeon.commerce.product.domain.Product;
import dev.smjeon.commerce.product.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByTopCategory(TopCategory topCategory);

    List<Product> findAllByType(ProductType type);
}
