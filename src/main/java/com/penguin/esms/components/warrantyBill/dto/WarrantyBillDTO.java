package com.penguin.esms.components.warrantyBill.dto;

import com.penguin.esms.components.warrantyProduct.dto.WarrantyProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyBillDTO {
    private String id;
    private String staffId;
    private String customerId;
    private Date warrantyDate;
    private List<WarrantyProductDTO> warrantyProducts = new ArrayList<>();
}
