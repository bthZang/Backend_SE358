package com.penguin.esms.envers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("enver")
@RequiredArgsConstructor
@RestController
public class AuditEnverInfoController {
    private final AuditEnverInfoService auditEnverInfoService;
    @GetMapping("history")
    public ResponseEntity<?> getHistoryByStaff(@RequestParam String username) throws ClassNotFoundException {
        return ResponseEntity.ok(auditEnverInfoService.view(auditEnverInfoService.getRecord(username)));
    }
}
