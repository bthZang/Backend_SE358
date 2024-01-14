package com.penguin.esms.components.saleBill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SaleBillService {
    private final SaleBillRepo saleBillRepo;
}
