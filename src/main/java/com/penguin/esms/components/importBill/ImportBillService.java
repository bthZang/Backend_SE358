package com.penguin.esms.components.importBill;

import com.penguin.esms.components.importBill.dto.ImportBillDTO;
import com.penguin.esms.components.importProduct.ImportProductEntity;
import com.penguin.esms.components.importProduct.ImportProductRepo;
import com.penguin.esms.components.importProduct.dto.ImportProductDTO;
import com.penguin.esms.components.product.ProductEntity;
import com.penguin.esms.components.product.ProductRepo;
import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImportBillService {
    private final ImportBillRepo importBillRepo;
    private final ProductRepo productRepo;
    private final DTOtoEntityMapper mapper;
    private final ImportProductRepo importProductRepo;

    public ImportBillEntity postImportBill(ImportBillDTO importBillDTO, Principal connectedUser) {
        StaffEntity staff = (StaffEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        importBillDTO.setStaffId(staff.getId());
        List<ImportProductDTO> importPrts = importBillDTO.getImportProducts();
        ImportBillEntity impot = updateFromDTO(importBillDTO, new ImportBillEntity());
        importBillRepo.save(impot);
        for (ImportProductDTO t : importPrts){
            ImportProductEntity impotPrt = updateFromDTO(t, new ImportProductEntity());
            Optional<ProductEntity> product = productRepo.findById(t.getProductId());
            impotPrt.setProduct(product.get());
            impotPrt.setImportBill(impot);
            importProductRepo.save(impotPrt);
            List<ImportProductEntity> importProducts = impot.getImportProducts();
            importProducts.add(impotPrt);
            impot.setImportProducts(importProducts);
        }
        importBillRepo.save(impot);
        return impot;
    }

    private ImportBillEntity updateFromDTO(ImportBillDTO importBillDTO, ImportBillEntity impot) {
        mapper.updateImportBillFromDto(importBillDTO, impot);
        return impot;
    }
    private ImportProductEntity updateFromDTO(ImportProductDTO dto, ImportProductEntity entity) {
        mapper.updateImportProductFromDto(dto, entity);
        return entity;
    }
}
