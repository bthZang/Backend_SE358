package com.penguin.esms.components.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByName(String name);
    List<CustomerEntity> findByNameContainingIgnoreCaseAndPhoneContainingIgnoreCaseAndIsStopped(String name, String phone, boolean isStopped);
    List<CustomerEntity> findByNameContainingIgnoreCaseAndIsStopped(String name, boolean isStopped);
    Optional<CustomerEntity> findById(String id);
    Optional<CustomerEntity> findByPhone(String phone);
    List<CustomerEntity> findByPhoneContainingIgnoreCase(String phone);
}
