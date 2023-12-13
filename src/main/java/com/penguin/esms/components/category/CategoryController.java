package com.penguin.esms.components.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "category")
@Getter
@Setter
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> post(@RequestBody CategoryEntity categoryEntity) {
        return ResponseEntity.ok(categoryService.postCategory(categoryEntity));
    }
}
