package com.penguin.esms.components.customer;

import com.penguin.esms.components.customer.dto.CustomerDTO;
import com.penguin.esms.entity.Error;
import com.penguin.esms.envers.AuditEnversInfo;
import com.penguin.esms.envers.AuditEnversInfoRepo;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import com.penguin.esms.utils.Random;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final DTOtoEntityMapper mapper;
    private final EntityManager entityManager;
    private final AuditEnversInfoRepo auditEnversInfoRepo;

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
    public static CustomerDTO random() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String name = Random.random(10, characters);
        String phone = "09" + Random.random(8, numbers);
        String address = Random.random(10, characters);
        return new CustomerDTO(name, phone, address);
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

    public List<?> getRevisions(String id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery query = auditReader.createQuery()
                .forRevisionsOfEntity(CustomerEntity.class, true, true)
                .add(AuditEntity.id().eq(id))
                .addProjection(AuditEntity.revisionNumber())
                .addProjection(AuditEntity.property("name"))
                .addProjection(AuditEntity.property("phone"))
                .addProjection(AuditEntity.property("address"))
                .addProjection(AuditEntity.revisionType())
                .addOrder(AuditEntity.revisionNumber().desc());

        List<AuditEnversInfo> audit = new ArrayList<AuditEnversInfo>();
        List<Object[]> objects = query.getResultList();
        for (int i = 0; i < objects.size(); i++) {
            Object[] objArray = objects.get(i);
            Optional<AuditEnversInfo> auditEnversInfoOptional = auditEnversInfoRepo.findById((int) objArray[0]);
            if (auditEnversInfoOptional.isPresent()) {
                AuditEnversInfo auditEnversInfo = auditEnversInfoOptional.get();
                CustomerDTO dto = new CustomerDTO((String) objArray[1], (String) objArray[2], (String) objArray[3]);
                auditEnversInfo.setRevision(dto);
                audit.add(auditEnversInfo);
            }
        }
        return audit;
    }
}
