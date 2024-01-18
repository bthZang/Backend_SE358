package com.penguin.esms.components.product;

import com.penguin.esms.components.category.CategoryEntity;
import com.penguin.esms.components.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, String>{
    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findByNameContainingIgnoreCaseAndIsStopped(String name, boolean isStopped);
    List<ProductEntity> findByNameContainingIgnoreCaseAndCategoryAndIsStopped(String name, CategoryEntity category, boolean isStopped);
    Optional<ProductEntity> findById(String id);
    List<ProductEntity> findByNameContainingIgnoreCase(String name);
    List<ProductEntity> findByNameContainingIgnoreCaseAndCategory(String name, CategoryEntity category);
}
