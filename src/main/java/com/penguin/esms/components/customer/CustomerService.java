package com.penguin.esms.components.customer;

import com.penguin.esms.components.customer.dto.CustomerDTO;
import com.penguin.esms.entity.Error;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final DTOtoEntityMapper mapper;

    public CustomerEntity getById(String id) {
        Optional<CustomerEntity> customer = customerRepo.findById(id);
        if (customer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, new Error("Customer not found").toString());
        }
        if (customer.get().getIsStopped() == true)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Customer has been banned ").toString());
        return customer.get();
    }

    public CustomerEntity getByPhone(String phone) {
        return customerRepo.findByPhone(phone).get();
    }

    public List<CustomerEntity> getCustomer(String name, String phone) {
        return customerRepo.findByNameContainingIgnoreCaseAndPhoneContainingIgnoreCaseAndIsStopped(name, phone, false);
    }

    public List<CustomerEntity> getBannedCustomer(String name) {
        return customerRepo.findByNameContainingIgnoreCaseAndIsStopped(name, true);
    }

    public CustomerEntity postCustomer(CustomerDTO dto) {
        Optional<CustomerEntity> customerOp = customerRepo.findByPhone(dto.getPhone());
        if (customerOp.isPresent())
            if (customerOp.get().getIsStopped() == true)
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, new Error("Customer has been banned ").toString());
        CustomerEntity customer = updateFromDTO(dto, new CustomerEntity());
//        CustomerEntity customer = dto;
        customer.setIsStopped(false);
        return customerRepo.save(customer);
    }

    public void removeCustomer(String id) {
        Optional<CustomerEntity> customer = customerRepo.findById(id);
        if (customer.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Customer not existed").toString());
        if (customer.get().getIsStopped() == true)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Customer has been banned ").toString());
        customer.get().setIsStopped(true);
        customerRepo.save(customer.get());
    }

    private CustomerEntity updateFromDTO(CustomerDTO customerDTO, CustomerEntity customer) {
        mapper.updateCustomerFromDto(customerDTO, customer);
        return customer;
    }

    public CustomerEntity update(CustomerDTO customerDTO, String id) throws IOException {
        Optional<CustomerEntity> customer = customerRepo.findById(id);
        if (customer.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Customer not existed").toString());
//        if (customer.get().getIsStopped() == true)
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, new Error("Customer has been banned ").toString());
        CustomerEntity customerEntity = updateFromDTO(customerDTO, customerRepo.findById(id).get());
        return customerRepo.save(customerEntity);
    }
}
