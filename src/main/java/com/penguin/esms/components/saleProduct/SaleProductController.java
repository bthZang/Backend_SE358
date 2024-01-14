package com.penguin.esms.components.saleProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path ="sale_product")
public class SaleProductController {
    private final SaleProductService service;
}
