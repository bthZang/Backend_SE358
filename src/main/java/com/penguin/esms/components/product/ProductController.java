package com.penguin.esms.components.product;

import com.penguin.esms.components.product.dto.ProductDTO;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path ="product")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductEntity> getAll(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String category) {
        return service.findRelatedCategory(name, category);
    }
    @GetMapping("discountinued")
    public List<ProductEntity> getAllDiscountinued(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String category) {
        return service.findDiscontinuedRelatedCategory(name,category);
    }

    @GetMapping("{id}")
    public ProductEntity getProduct(@PathVariable String id) {
        return service.getProductById(id);
    }

    @GetMapping("history/{id}")
    public List<?> getALlHistory(@PathVariable String id) {
        return service.getRevisionsForProduct(id);
    }

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.add(productDTO));
    }

     @PutMapping("{id}")
    public ResponseEntity<?> putProduct(@RequestParam @Nullable MultipartFile photo, @Valid ProductDTO productDTO, @PathVariable String id) throws IOException {
        return ResponseEntity.ok(service.update(productDTO, id, photo));
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable String id) {
        service.remove(id);
    }
}
