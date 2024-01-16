package com.penguin.esms.components.warrantyBill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class WarrantyBillService {
    private final WarrantyBillRepo warrantyBillRepo;
}
