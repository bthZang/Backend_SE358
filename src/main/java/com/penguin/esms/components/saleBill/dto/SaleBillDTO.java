package com.penguin.esms.components.saleBill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
