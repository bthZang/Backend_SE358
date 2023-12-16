package com.penguin.esms.components.supplier;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService service;

    @PutMapping("{id}")
    public ResponseEntity<?> put(@Valid com.penguin.esms.components.supplier.dto.SupplierDTO supplierDTO, @PathVariable String id) {
        return ResponseEntity.ok(service.update(supplierDTO, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        SupplierEntity supplier = service.remove(id);
        return ResponseEntity.ok().build();
    }
}
