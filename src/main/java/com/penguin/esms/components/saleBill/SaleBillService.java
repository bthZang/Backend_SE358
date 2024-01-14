package com.penguin.esms.components.saleBill;

import com.penguin.esms.components.customer.CustomerEntity;
import com.penguin.esms.components.customer.CustomerRepo;
import com.penguin.esms.components.product.ProductEntity;
import com.penguin.esms.components.product.ProductRepo;
import com.penguin.esms.components.saleBill.dto.SaleBillDTO;
import com.penguin.esms.components.saleProduct.SaleProductEntity;
import com.penguin.esms.components.saleProduct.SaleProductRepo;
import com.penguin.esms.components.saleProduct.dto.SaleProductDTO;
import com.penguin.esms.components.staff.StaffEntity;
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
public class SaleBillService {
    private final SaleBillRepo saleBillRepo;
    private final SaleProductRepo saleProductRepo;
    private final DTOtoEntityMapper mapper;
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;

    public SaleBillEntity post(SaleBillDTO saleBillDTO, StaffEntity staff) {
        saleBillDTO.setStaffId(staff.getId());
        List<SaleProductDTO> salePrts = saleBillDTO.getSaleProducts();
        CustomerEntity customer = customerRepo.findById(saleBillDTO.getCustomerId()).get();
        SaleBillEntity sale = updateFromDTO(saleBillDTO, new SaleBillEntity());
        sale.setCustomer(customer);
        saleBillRepo.save(sale);
        for (SaleProductDTO t : salePrts) {
            SaleProductEntity salePrt = updateFromDTO(t, new SaleProductEntity());
            Optional<ProductEntity> product = productRepo.findById(t.getProductId());
            salePrt.setProduct(product.get());
            salePrt.setSaleBill(sale);
            saleProductRepo.save(salePrt);
            List<SaleProductEntity> saleProducts = sale.getSaleProducts();
            saleProducts.add(salePrt);
            sale.setSaleProducts(saleProducts);
        }
        saleBillRepo.save(sale);
        return sale;
    }

    private SaleBillEntity updateFromDTO(SaleBillDTO saleBillDTO, SaleBillEntity sale) {
        mapper.updateSaleBillFromDto(saleBillDTO, sale);
        return sale;
    }

    private SaleProductEntity updateFromDTO(SaleProductDTO dto, SaleProductEntity entity) {
        mapper.updateSaleProductFromDto(dto, entity);
        return entity;
    }
}
