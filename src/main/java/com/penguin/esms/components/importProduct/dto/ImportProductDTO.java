package com.penguin.esms.components.importProduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImportProductDTO {
    private String productId;
    private Integer quantity;
    private Long price;

    public ImportProductDTO(String productId, Integer quantity, Long price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
