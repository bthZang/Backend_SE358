package com.penguin.esms.components.category;

import com.penguin.esms.components.staff.StaffEntity;
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
        return categoryRepo.findByNameContainingIgnoreCase(name);
    }

    public CategoryEntity postCategory(CategoryEntity categoryEntity) {
        if (categoryRepo.findByName(categoryEntity.getName()).isPresent())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Category existed");
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

    public void delete(String id){
        if (categoryRepo.findById(id).isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not existed");
        categoryRepo.deleteById(id);
    }
}
