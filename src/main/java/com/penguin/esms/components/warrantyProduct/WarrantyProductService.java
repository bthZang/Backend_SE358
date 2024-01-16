package com.penguin.esms.components.warrantyProduct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Service
@Getter
@Setter
@RequiredArgsConstructor
public class WarrantyProductService {
    private final WarrantyProductRepo repo;
}
