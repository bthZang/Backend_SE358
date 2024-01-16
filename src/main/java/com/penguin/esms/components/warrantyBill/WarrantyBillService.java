package com.penguin.esms.components.warrantyBill;

import com.penguin.esms.components.customer.CustomerEntity;
import com.penguin.esms.components.customer.CustomerRepo;
import com.penguin.esms.components.product.ProductEntity;
import com.penguin.esms.components.product.ProductRepo;
import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.components.warrantyBill.dto.WarrantyBillDTO;
import com.penguin.esms.components.warrantyProduct.WarrantyProductEntity;
import com.penguin.esms.components.warrantyProduct.WarrantyProductRepo;
import com.penguin.esms.components.warrantyProduct.dto.WarrantyProductDTO;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class WarrantyBillService {
    private final WarrantyBillRepo warrantyBillRepo;
    private final WarrantyProductRepo warrantyProductRepo;
    private final DTOtoEntityMapper mapper;
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;


    public WarrantyBillEntity postWarrantyBill(WarrantyBillDTO dto, StaffEntity staff) {
        dto.setStaffId(staff.getId());
        List<WarrantyProductDTO> warrantyProducts = dto.getWarrantyProducts();
        CustomerEntity customer = customerRepo.findById(dto.getCustomerId()).get();
        WarrantyBillEntity warranty = updateFromDTO(dto, new WarrantyBillEntity());
        warranty.setCustomer(customer);
        warrantyBillRepo.save(warranty);
        for (WarrantyProductDTO t : warrantyProducts) {
            WarrantyProductEntity warrantyProduct = updateFromDTO(t, new WarrantyProductEntity());
            Optional<ProductEntity> product = productRepo.findById(t.getProductId());
            warrantyProduct.setProduct(product.get());
            warrantyProduct.setWarrantyBill(warranty);
            warrantyProductRepo.save(warrantyProduct);
            List<WarrantyProductEntity> warrantyPrts = warranty.getWarrantyProducts();
            warrantyPrts.add(warrantyProduct);
            warranty.setWarrantyProducts(warrantyPrts);
        }
        warrantyBillRepo.save(warranty);
        return warranty;
    }
    private WarrantyBillEntity updateFromDTO(WarrantyBillDTO dto, WarrantyBillEntity entity) {
        mapper.updateWarrantyBillFromDto(dto, entity);
        return entity;
    }

    private WarrantyProductEntity updateFromDTO(WarrantyProductDTO dto, WarrantyProductEntity entity) {
        mapper.updateWarrantyProductFromDto(dto, entity);
        return entity;
    }
}
