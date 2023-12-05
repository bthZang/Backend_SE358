package com.penguin.esms.components.staff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffEntity, String> {
    List<StaffEntity> findByNameContainingIgnoreCase(String name);
    Optional<StaffEntity> findByEmail(String email);
    Optional<StaffEntity> findById(String id);
}
