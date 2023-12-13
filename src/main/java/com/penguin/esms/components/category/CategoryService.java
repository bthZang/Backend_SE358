package com.penguin.esms.components.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
@Getter
@Setter
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    public CategoryEntity postCategory(CategoryEntity categoryEntity) {
        if (categoryRepo.findByName(categoryEntity.getName()).isPresent())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Category existed");
        return categoryRepo.save(categoryEntity);
    }
}
