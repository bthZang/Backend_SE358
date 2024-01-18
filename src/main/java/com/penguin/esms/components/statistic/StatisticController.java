package com.penguin.esms.components.statistic;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("statistic")
@RequiredArgsConstructor
@ControllerAdvice
public class StatisticController {
    private final StatisticService service;

    @GetMapping("name/{name}")
    public StatisticEntity getName(@PathVariable String name) {
        return service.getByName(name);
    }

    @GetMapping("revenue")
    public ResponseEntity<?> getRevenueByPeriod(@RequestParam long start, @RequestParam long end) throws JsonProcessingException {
        return ResponseEntity.ok(service.getRevenuePeriod(new Date(start), new Date(end)));
    }

    @GetMapping("category")
    public ResponseEntity<?> getRevenueByCategory(@RequestParam long start, @RequestParam long end) throws JsonProcessingException {
        return ResponseEntity.ok(service.revenueByCategory(new Date(start), new Date(end)));
    }
    @GetMapping("revenue/date")
    public ResponseEntity<?> getRevenueByDate(@RequestParam long date) throws JsonProcessingException {
        return ResponseEntity.ok(service.getRevenueDate(new Date(date)));
    }

}
