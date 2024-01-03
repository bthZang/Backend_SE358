package com.penguin.esms.components.importProduct;

import com.penguin.esms.components.importBill.ImportBillRepo;
import com.penguin.esms.components.product.ProductRepo;
import com.penguin.esms.mapper.DTOtoEntityMapper;
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
}
