package com.penguin.esms.components.supplier;

import com.penguin.esms.components.supplier.dto.SupplierDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(path = "supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService service;
    @PostMapping
    public ResponseEntity<?> post(@Valid SupplierDTO supplierDTO) {
        return ResponseEntity.ok(service.add(supplierDTO));
    }
}
