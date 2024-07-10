package org.pronet.shoppie.repositories;

import org.pronet.shoppie.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE is_active = 1 ORDER BY id DESC LIMIT 4")
    List<Product> takeActiveTopProducts();
    List<Product> findByIsActiveTrue();
    List<Product> findByCategory(String category);
    Boolean existsByName(String name);
}
