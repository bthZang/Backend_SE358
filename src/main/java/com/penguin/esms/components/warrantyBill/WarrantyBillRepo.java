package com.penguin.esms.components.warrantyBill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarrantyBillRepo  extends JpaRepository<WarrantyBillEntity, String> {
    Optional<WarrantyBillEntity> findById(String id);
}
