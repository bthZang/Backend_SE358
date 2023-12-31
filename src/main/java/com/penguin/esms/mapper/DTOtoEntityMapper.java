package com.penguin.esms.mapper;

import com.penguin.esms.components.category.CategoryDTO;
import com.penguin.esms.components.category.CategoryEntity;
import com.penguin.esms.components.importBill.ImportBillEntity;
import com.penguin.esms.components.importBill.dto.ImportBillDTO;
import com.penguin.esms.components.importProduct.ImportProductEntity;
import com.penguin.esms.components.importProduct.dto.ImportProductDTO;
import com.penguin.esms.components.product.dto.ProductDTO;
import com.penguin.esms.components.product.ProductEntity;
import com.penguin.esms.components.staff.StaffDTO;
import com.penguin.esms.components.staff.StaffEntity;
import com.penguin.esms.components.supplier.SupplierEntity;
import com.penguin.esms.components.supplier.dto.SupplierDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DTOtoEntityMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDto(CategoryDTO dto, @MappingTarget CategoryEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductDTO dto, @MappingTarget ProductEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStaffFromDto(StaffDTO dto, @MappingTarget StaffEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplierFromDto(SupplierDTO dto, @MappingTarget SupplierEntity entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateImportBillFromDto(ImportBillDTO dto, @MappingTarget ImportBillEntity entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateImportProductFromDto(ImportProductDTO dto, @MappingTarget ImportProductEntity entity);
    default List<SupplierEntity> map(List<String> value) {
        return value.stream().map(v -> new SupplierEntity()).toList();
    }
}