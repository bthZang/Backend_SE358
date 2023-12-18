package com.penguin.esms.components.product;

import com.penguin.esms.components.category.CategoryEntity;
import com.penguin.esms.components.category.CategoryRepo;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.RequiredArgsConstructor;
import com.penguin.esms.entity.Error;
import lombok.Getter;
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


    public List<ProductEntity> findByName(String name, String categoryName) {
            if (categoryName != null && !categoryName.isEmpty()) {
                Optional<CategoryEntity> optionalCategory = categoryRepo.findByName(categoryName);
                if (optionalCategory.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, new Error("category not found").toString());
                }
                return productRepo.findByNameContainingIgnoreCaseAndCategory(name, optionalCategory.get());
            }
            return productRepo.findByNameContainingIgnoreCase(name);
        }

        public ProductEntity getProduct(String productId) {
            Optional<ProductEntity> product = productRepo.findById(productId);
            if (product.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            return product.get();
        }

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

public ProductEntity update(ProductDTO productDTO, String id, MultipartFile photo) throws IOException {
        if (productRepo.findById(id).isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not existed");
        ProductEntity product = updateFromDTO(productDTO, productRepo.findById(id).get());
        if (photo != null) {
            List<String> parsedURL = Arrays.stream(product.getPhotoURL().split("/")).toList();
            amazonS3Service.deleteFile(parsedURL.get(parsedURL.size() - 1));
            String objectURL = amazonS3Service.updateFile(photo, product.getName() + "_" + photo.getOriginalFilename());
            product.setPhotoURL(objectURL);
        }
        return productRepo.save(product);
    }

private ProductEntity updateFromDTO(ProductDTO productDTO, ProductEntity product) {
        mapper.updateProductFromDto(productDTO, product);
        if (productDTO.getCategoryId() != null) {
            if (productDTO.getCategoryId().isEmpty()) {
                product.setCategory(null);
            } else {
                Optional<CategoryEntity> category = categoryRepo.findById(productDTO.getCategoryId());
                if (category.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, new Error("Category not found").toString());
                }
                product.setCategory(category.get());
            }
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
