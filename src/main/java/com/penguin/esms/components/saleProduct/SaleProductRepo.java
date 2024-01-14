package com.penguin.esms.components.saleProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleProductRepo extends JpaRepository<SaleProductEntity,String> {
    Optional<SaleProductEntity> findById(String id);
    List<SaleProductEntity> findBySaleBillId(String saleBillId);
}
