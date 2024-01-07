package com.penguin.esms.components.importBill;

import com.penguin.esms.components.importBill.dto.ImportBillDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "import")
@RequiredArgsConstructor
public class ImportBillController {
    private final ImportBillService importBillService;
    @GetMapping("{id}")
    public List<?> get(@PathVariable String id) {
        return importBillService.getRevisions(id);
    }
    @GetMapping()
    public List<?> get() {
        return importBillService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> post(@RequestBody ImportBillDTO importBillDTO, Principal connectedUser) {
        return ResponseEntity.ok(importBillService.postImportBill(importBillDTO, connectedUser));
    }
    @GetMapping("history")
    public ResponseEntity<?> getAll(@RequestParam long start, @RequestParam long end) {
        if (start == end) return ResponseEntity.ok(importBillService.getAll());
        return ResponseEntity.ok(importBillService.getAllRevisions(new Date(start), new Date(end)));
    }
}
