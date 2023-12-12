package com.penguin.esms.components.product;

import com.penguin.esms.entity.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    public ProductEntity remove(String id) {
        Optional<ProductEntity> productEntityOptional = productRepo.findById(id);
        if (productEntityOptional.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Product not existed").toString());
        productRepo.deleteById(id);
        return productEntityOptional.get();
    }
}
