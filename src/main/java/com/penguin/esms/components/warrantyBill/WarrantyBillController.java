package com.penguin.esms.components.warrantyBill;

import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.components.warrantyBill.dto.WarrantyBillDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "warranty")
@RequiredArgsConstructor
public class WarrantyBillController {
    private final WarrantyBillService warrantyBillService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> post(@RequestBody WarrantyBillDTO dto, Principal connectedUser) {
        StaffEntity staff = (StaffEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return ResponseEntity.ok(warrantyBillService.postWarrantyBill(dto,staff));
    }
}
