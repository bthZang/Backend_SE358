package com.penguin.esms.components.importBill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImportBillRepo extends JpaRepository<ImportBillEntity, String> {
    Optional<ImportBillEntity> findById(String id);
}
