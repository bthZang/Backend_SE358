package com.penguin.esms.components.customer.dto;

import com.penguin.esms.components.saleBill.dto.SaleBillDTO;
import com.penguin.esms.components.warrantyBill.dto.WarrantyBillDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@RequiredArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String name;
    private String phone;
    private String address;
    private List<WarrantyBillDTO> warrantyBills;
    private List<SaleBillDTO> saleBills;


    public CustomerDTO(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
