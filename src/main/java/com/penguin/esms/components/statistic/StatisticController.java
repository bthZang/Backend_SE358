package com.penguin.esms.components.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("statistic")
@RequiredArgsConstructor
@ControllerAdvice
public class StatisticController {
    private final StatisticService service;

}
