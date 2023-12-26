package com.penguin.esms.components.supplier;

import com.penguin.esms.components.supplier.dto.SupplierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.penguin.esms.entity.Error;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepo supplierRepo;
    private final DTOtoEntityMapper mapper;

    public Optional<SupplierEntity> findById(String supplierId) {
        return supplierRepo.findById(supplierId);
    }

    public List<SupplierEntity> findByName(String name) {
        return supplierRepo.findByNameContainingIgnoreCase(name);
    }

    public SupplierEntity add(SupplierDTO supplierDTO) {
        SupplierEntity supplier = new SupplierEntity();
        mapper.updateSupplierFromDto(supplierDTO, supplier);
        supplier.setNote(supplierDTO.getNote());
        return supplierRepo.save(supplier);
    }
}
    public SupplierEntity update(SupplierDTO supplierDTO, String id) {
        Optional<SupplierEntity> optionalSupplier = supplierRepo.findById(id);
        if (optionalSupplier.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, new Error("Supplier not found").toString());
        }
        SupplierEntity supplier = optionalSupplier.get();
        mapper.updateSupplierFromDto(supplierDTO, supplier);
        if (supplierDTO.getNote() != null) supplier.setNote(supplierDTO.getNote());
        return supplierRepo.save(supplier);
    }

    public void remove(String id) {
        Optional<SupplierEntity> supplierEntityOptional = supplierRepo.findById(id);
        if (supplierEntityOptional.isEmpty()) {
            if (supplierEntityOptional.get().getIsStopped() == true)
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, new Error("Supplier has terminated cooperation ").toString());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Supplier not found").toString());
        }
        supplierEntityOptional.get().setIsStopped(true);
        supplierRepo.save(supplierEntityOptional.get());
    }
}
