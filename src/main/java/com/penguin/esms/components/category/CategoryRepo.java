package com.penguin.esms.components.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<CategoryEntity, String> {
    List<CategoryEntity> findByNameContainingIgnoreCase(String name);
    Optional<CategoryEntity> findByName(String name);
}
