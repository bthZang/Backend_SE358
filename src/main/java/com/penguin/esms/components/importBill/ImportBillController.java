package com.penguin.esms.components.importBill;

import com.penguin.esms.components.importBill.dto.ImportBillDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@RestController
@RequestMapping(path = "import")
@RequiredArgsConstructor
public class ImportBillController {
    private final ImportBillService importBillService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> post(@RequestBody ImportBillDTO importBillDTO, Principal connectedUser) {
        return ResponseEntity.ok(importBillService.postImportBill(importBillDTO, connectedUser));
    }
}
