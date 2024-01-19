package com.penguin.esms.controller;

import com.penguin.esms.components.importBill.ImportBillService;
import com.penguin.esms.components.saleBill.SaleBillService;
import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.components.warrantyBill.WarrantyBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "data")
@RequiredArgsConstructor
public class Data {
    private final ImportBillService importBillService;
    private final WarrantyBillService warrantyBillService;
    private final SaleBillService saleBillService;

    @PostMapping("import")
    public void randomImport(Principal connectedUser) {
        StaffEntity staff = (StaffEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        for (int i = 0; i <= 10; i++)
            importBillService.postImportBill(importBillService.random(), staff);
    }

    @PostMapping("sale")
    public void randomSale(Principal connectedUser) {
        StaffEntity staff = (StaffEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        for (int i = 0; i <= 20; i++)
            saleBillService.post(saleBillService.random(), staff);
    }

    @PostMapping("warranty")
    public void randomWarranty(Principal connectedUser) {
        StaffEntity staff = (StaffEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        for (int i = 0; i <= 10; i++)
            warrantyBillService.postWarrantyBill(warrantyBillService.random(), staff);
    }

}
