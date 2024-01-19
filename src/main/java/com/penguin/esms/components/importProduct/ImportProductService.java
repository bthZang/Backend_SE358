package com.penguin.esms.components.importProduct;

import com.penguin.esms.components.importBill.ImportBillRepo;
import com.penguin.esms.components.importProduct.dto.ImportProductDTO;
import com.penguin.esms.components.product.ProductEntity;
import com.penguin.esms.components.product.ProductRepo;
import com.penguin.esms.components.product.ProductService;
import com.penguin.esms.components.product.dto.ProductDTO;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import com.penguin.esms.utils.Random;
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
public class ImportProductService {
    private final ProductService productService;
    private final ImportProductRepo importProductRepo;
    private final ProductRepo productRepo;
    private final ImportBillRepo importBillRepo;
    private final DTOtoEntityMapper mapper;

    public ImportProductEntity getImportProduct(String importProductId) {
        Optional<ImportProductEntity> importProduct = importProductRepo.findById(importProductId);
        if (importProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return importProduct.get();
    }

    public ImportProductEntity getImportBill(String importBillId) {
        Optional<ImportProductEntity> importProduct = importProductRepo.findById(importBillId);
        if (importProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return importProduct.get();
    }

    public ImportProductDTO random() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numbers = "123456789";
        ProductDTO productDTO = productService.random();
        ProductEntity productEntity = productService.add(productDTO);
        String productId = productEntity.getId();
        Long price = Long.valueOf(Random.random(Integer.valueOf(Random.random(1, "1234567")), numbers))*1000l;
        Integer quantity = Integer.valueOf(Random.random(Integer.valueOf(Random.random(1, "123")), numbers));
        return new ImportProductDTO(productId, quantity, price);
    }
}
