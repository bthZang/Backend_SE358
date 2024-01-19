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

    public WarrantyBillDTO(String staffId, String customerId, Date warrantyDate) {
        this.staffId = staffId;
        this.customerId = customerId;
        this.warrantyDate = warrantyDate;
    }

    public WarrantyBillDTO(String staffId, String customerId, Date warrantyDate, String id) {
        this.staffId = staffId;
        this.customerId = customerId;
        this.warrantyDate = warrantyDate;
        this.setId(id);
    }

    public WarrantyBillDTO(String customerId, List<WarrantyProductDTO> warrantyProducts) {
        this.customerId = customerId;
//        this.warrantyDate = warrantyDate;
        this.warrantyProducts = warrantyProducts;
    }
}
