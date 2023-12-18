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

    @GetMapping
    public List<ProductEntity> getAl(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String category) {
        return service.findByName(name, category);
    }

    @GetMapping("{id}")
    public ProductEntity getProduct(@PathVariable String id) {
        return service.getProduct(id);
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
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        ProductEntity product = service.remove(id);
        return ResponseEntity.ok().build();
    }
}
