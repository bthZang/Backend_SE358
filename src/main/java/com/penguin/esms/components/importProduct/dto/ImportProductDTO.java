package com.penguin.esms.components.importProduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ImportProductDTO {
    private String productId;
    private Integer quantity;
    private Long price;

}
