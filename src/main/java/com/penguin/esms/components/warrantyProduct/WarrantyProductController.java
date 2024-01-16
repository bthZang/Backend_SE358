package com.penguin.esms.components.warrantyProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path ="warranty_product")
public class WarrantyProductController {
    private final WarrantyProductService service;
}
