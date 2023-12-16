package com.penguin.esms.components.supplier;

import com.penguin.esms.components.permission.EntityType;
import com.penguin.esms.components.permission.PermissionEntity;
import com.penguin.esms.components.permission.PermissionRepo;
import com.penguin.esms.components.permission.PermissionType;
import com.penguin.esms.components.permission.dto.ItemPermission;
import com.penguin.esms.components.product.ProductEntity;
import com.penguin.esms.components.product.ProductDTO;
import com.penguin.esms.components.staff.Role;
import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.components.supplier.SupplierDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final PermissionRepo permissionRepo;


 @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        SupplierEntity supplier = service.remove(id);
        return ResponseEntity.ok().build();
    }
}
