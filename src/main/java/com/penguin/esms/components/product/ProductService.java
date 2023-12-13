package com.penguin.esms.components.product;


import com.penguin.esms.components.category.CategoryEntity;
import com.penguin.esms.components.category.CategoryRepo;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.RequiredArgsConstructor;
import com.penguin.esms.entity.Error;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final DTOtoEntityMapper mapper;

    public ProductEntity add(ProductDTO productDTO) {
        if (productRepo.findByName(productDTO.getName()).isPresent())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Product existed");
        ProductEntity product = new ProductEntity();
        mapper.updateProductFromDto(productDTO, product);
        if (productDTO.getCategoryId() != null) {
            Optional<CategoryEntity> category = categoryRepo.findById(productDTO.getCategoryId());
            if (category.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
            }
            product.setCategory(category.get());
        }
        return productRepo.save(product);
    }
    public ProductEntity remove(String id) {
        Optional<ProductEntity> productEntityOptional = productRepo.findById(id);
        if (productEntityOptional.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Product not existed").toString());
        productRepo.deleteById(id);
        return productEntityOptional.get();
    }
}
