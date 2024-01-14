package com.penguin.esms.components.saleBill.dto;

import com.penguin.esms.components.saleProduct.dto.SaleProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleBillDTO {
    private String staffId;
    private String id;
    private String customerId;
    private String paymentMethod;
    private Float discount;
    private List<SaleProductDTO> saleProducts;
}
