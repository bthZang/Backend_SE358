package com.penguin.esms.components.supplier;

import com.penguin.esms.components.supplier.dto.SupplierDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService service;

    @GetMapping
    public List<SupplierEntity> getAll(@RequestParam(defaultValue = "") String name) {
        return service.findByName(name);
    }

    @GetMapping("termination")
    public List<SupplierEntity> getAllTermination(@RequestParam(defaultValue = "") String name) {
        return service.findTermination(name);
    }
    
    @GetMapping("{id}")
    public SupplierEntity getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
        public ResponseEntity<?> post(@Valid SupplierDTO supplierDTO) {
            return ResponseEntity.ok(service.add(supplierDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> put(@Valid SupplierDTO supplierDTO, @PathVariable String id) {
        return ResponseEntity.ok(service.update(supplierDTO, id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        service.remove(id);
    }
}
