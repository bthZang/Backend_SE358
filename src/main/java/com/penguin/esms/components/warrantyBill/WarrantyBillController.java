package com.penguin.esms.components.warrantyBill;

import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.components.warrantyBill.dto.WarrantyBillDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

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

    @GetMapping("{id}")
    public List<?> get(@PathVariable String id) {
        return warrantyBillService.getRevisions(id);
    }
    @GetMapping()
    public List<?> get() {
        return warrantyBillService.getAll();
    }
    @GetMapping("history")
    public ResponseEntity<?> getAll(@RequestParam long start, @RequestParam long end) {
        if (start == end) return ResponseEntity.ok(warrantyBillService.getAll());
        return ResponseEntity.ok(warrantyBillService.getAllRevisions(new Date(start), new Date(end)));
    }
}
