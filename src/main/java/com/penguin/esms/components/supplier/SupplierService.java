package com.penguin.esms.components.supplier;

import com.penguin.esms.components.supplier.dto.SupplierDTO;
import com.penguin.esms.envers.AuditEnversInfo;
import com.penguin.esms.envers.AuditEnversInfoRepo;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import com.penguin.esms.entity.Error;
import com.penguin.esms.mapper.DTOtoEntityMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final AuditEnversInfoRepo auditEnversInfoRepo;
    private final SupplierRepo supplierRepo;
    private final DTOtoEntityMapper mapper;

    public List<SupplierEntity> findByName(String name) {
        return supplierRepo.findByNameContainingIgnoreCaseAndIsStopped(name, false);
    }

    public List<SupplierEntity> findTermination(String name) {
        return supplierRepo.findByNameContainingIgnoreCaseAndIsStopped(name, true);
    }

    public SupplierEntity getOne(String id) {
        Optional<SupplierEntity> optionalSupplier = supplierRepo.findById(id);
        if (optionalSupplier.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, new Error("Supplier not found").toString());
        }
        if (optionalSupplier.get().getIsStopped() == true)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Supplier has terminated cooperation");
        return optionalSupplier.get();
    }

   public SupplierEntity add(SupplierDTO supplierDTO) {
        Optional<SupplierEntity> supplierEntityOptional = supplierRepo.findByName(supplierDTO.getName());
        if (supplierEntityOptional.isPresent()) {
            if (supplierEntityOptional.get().getIsStopped() == true)
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, new Error("Supplier has terminated cooperation ").toString());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, new Error("Supplier existed").toString());
        }
        SupplierEntity supplier = new SupplierEntity();
        mapper.updateSupplierFromDto(supplierDTO, supplier);
        supplier.setNote(supplierDTO.getNote());
        supplier.setIsStopped(false);
        return supplierRepo.save(supplier);
    }

    public SupplierEntity update(SupplierDTO supplierDTO, String id) {
        Optional<SupplierEntity> optionalSupplier = supplierRepo.findById(id);
        if (optionalSupplier.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, new Error("Supplier not found").toString());
        }
        SupplierEntity supplier = optionalSupplier.get();
        mapper.updateSupplierFromDto(supplierDTO, supplier);
        if (supplierDTO.getNote() != null) supplier.setNote(supplierDTO.getNote());
        return supplierRepo.save(supplier);
    }

    public void remove(String id) {
        Optional<SupplierEntity> supplierEntityOptional = supplierRepo.findById(id);
        if (supplierEntityOptional.isEmpty()) {
            if (supplierEntityOptional.get().getIsStopped() == true)
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, new Error("Supplier has terminated cooperation ").toString());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, new Error("Supplier not found").toString());
        }
        supplierEntityOptional.get().setIsStopped(true);
        supplierRepo.save(supplierEntityOptional.get());
    }

    @Transactional
    public List<?> getRevisions(String id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery query = auditReader.createQuery()
                .forRevisionsOfEntity(SupplierEntity.class, true, true)
                .add(AuditEntity.id().eq(id))
                .addProjection(AuditEntity.revisionNumber())
                .addProjection(AuditEntity.property("name"))
                .addProjection(AuditEntity.property("phone"))
                .addProjection(AuditEntity.property("email"))
                .addProjection(AuditEntity.property("address"))
                .addProjection(AuditEntity.revisionType())
                .addOrder(AuditEntity.revisionNumber().desc());

        List<AuditEnversInfo> audit = new ArrayList<AuditEnversInfo>();
        List<Object[]> objects = query.getResultList();
        for(int i=0; i< objects.size();i++){
            Object[] objArray = objects.get(i);
            Optional<AuditEnversInfo> auditEnversInfoOptional = auditEnversInfoRepo.findById((int) objArray[0]);
            if (auditEnversInfoOptional.isPresent()) {
                AuditEnversInfo auditEnversInfo = auditEnversInfoOptional.get();
                SupplierDTO dto = new SupplierDTO((String) objArray[1],  (String) objArray[2], (String) objArray[3], (String) objArray[4]);
                auditEnversInfo.setRevision(dto);
                audit.add(auditEnversInfo);
            }
        }
        entityManager.close();
        return audit;
    }
}
