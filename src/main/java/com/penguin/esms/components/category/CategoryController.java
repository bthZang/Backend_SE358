package com.penguin.esms.components.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "category")
@Getter
@Setter
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("discontinued")
    public List<CategoryEntity> getALlDiscontinued(@RequestParam(defaultValue = "") String name) {
        return categoryService.getDiscontinuedCategory(name);
    }

    @GetMapping
    public List<CategoryEntity> getALl(@RequestParam(defaultValue = "") String name) {
        return categoryService.getCategory(name);
    }
    @GetMapping("history/{id}")
    public List<?> getALlHistory(@PathVariable String id) {
        return categoryService.getRevisionsForCategory(id);
    }
    @GetMapping("{id}")
    public CategoryEntity getItem(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> post(@RequestBody CategoryEntity categoryEntity) {
        return ResponseEntity.ok(categoryService.postCategory(categoryEntity));
    }


    @PutMapping(path = "{id}")
    public CategoryEntity edit(CategoryDTO categoryDTO, @PathVariable String id) {
        return categoryService.editCategory(categoryDTO, id);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }

}
