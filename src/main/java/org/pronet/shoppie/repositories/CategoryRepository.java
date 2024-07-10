package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM categories WHERE is_active = 1 ORDER BY id LIMIT 4")
    List<Category> takeActiveTopCategories();
    List<Category> findByIsActiveTrue();
    Boolean existsByName(String name);
}
