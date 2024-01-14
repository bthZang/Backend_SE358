package com.penguin.esms.components.saleProduct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class SaleProductService {
    private final SaleProductRepo repo;
}
