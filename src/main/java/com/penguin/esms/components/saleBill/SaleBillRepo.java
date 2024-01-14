package com.penguin.esms.components.saleBill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleBillRepo extends JpaRepository<SaleBillEntity, String> {
    Optional<SaleBillEntity> findById(String id);
}
