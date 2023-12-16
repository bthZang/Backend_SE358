package com.penguin.esms.components.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path ="product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.add(productDTO));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        ProductEntity product = service.remove(id);
        return ResponseEntity.ok().build();
    }
}
