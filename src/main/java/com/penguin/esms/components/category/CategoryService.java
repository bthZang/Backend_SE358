package com.penguin.esms.components.category;

import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.entity.Error;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    private final DTOtoEntityMapper mapper;

    public CategoryEntity getCategoryById(String id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepo.findById(id);
        if (categoryEntityOptional.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not existed");
        else {
            return categoryEntityOptional.get();
        }
    }
    public List<CategoryEntity> getCategory(String name) {
        return categoryRepo.findByNameContainingIgnoreCaseAndIsStopped(name, false);
    }
    public List<CategoryEntity> getDiscontinuedCategory(String name) {
        return categoryRepo.findByNameContainingIgnoreCaseAndIsStopped(name, true);
    }

    public CategoryEntity postCategory(CategoryEntity categoryEntity) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepo.findByName(categoryEntity.getName());
        if (categoryEntityOptional.isPresent()) {
            if (categoryEntityOptional.get().getIsStopped() == true)
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, new Error("Category has been discontinued ").toString());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, new Error("Category existed").toString());
        }
        categoryEntity.setIsStopped(false);
        return categoryRepo.save(categoryEntity);
    }


    public CategoryEntity editCategory(CategoryDTO categoryDTO, String id) {
        CategoryEntity category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not existed"
                ));

        mapper.updateCategoryFromDto(categoryDTO, category);
        return categoryRepo.save(category);
    }

    public void deleteCategory(String id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepo.findById(id);
        if (categoryEntityOptional.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not existed");
        categoryEntityOptional.get().setIsStopped(true);
        categoryRepo.save(categoryEntityOptional.get());
    }
}
