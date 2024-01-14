package com.penguin.esms.components.saleBill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "sale")
@RequiredArgsConstructor
@Getter
@Setter
public class SaleBillController {
    private final SaleBillService saleBillService;
}
