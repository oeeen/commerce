package dev.smjeon.commerce.category.repository;

import dev.smjeon.commerce.category.domain.CategoryName;
import dev.smjeon.commerce.category.domain.TopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<TopCategory, Long> {
    Optional<TopCategory> findByName(CategoryName categoryName);
}
