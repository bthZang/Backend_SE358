package com.penguin.esms.components.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path ="product")
public class ProductController {
    private final ProductService service;

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        ProductEntity product = service.remove(id);
        return ResponseEntity.ok().build();
    }
}
