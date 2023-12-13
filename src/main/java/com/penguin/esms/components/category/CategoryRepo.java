package com.penguin.esms.components.category;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<CategoryEntity, String> {
    Optional<CategoryEntity> findByName(String name);
}
