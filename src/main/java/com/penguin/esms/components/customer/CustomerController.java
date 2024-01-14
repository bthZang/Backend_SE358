package com.penguin.esms.components.customer;

import com.penguin.esms.components.customer.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("{id}")
    public CustomerEntity getById(@PathVariable String id) {
        return customerService.getById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> post(@RequestBody CustomerDTO dto) {
        return ResponseEntity.ok(customerService.postCustomer(dto));
    }
    @DeleteMapping("{id}")
    public void remove(@PathVariable String id) {
        customerService.removeCustomer(id);
    }

}
