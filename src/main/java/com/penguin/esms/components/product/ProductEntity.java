package com.penguin.esms.components.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.penguin.esms.components.category.CategoryEntity;
import com.penguin.esms.components.importProduct.ImportProductEntity;
import com.penguin.esms.components.saleProduct.SaleProductEntity;
import com.penguin.esms.components.supplier.SupplierEntity;
import com.penguin.esms.components.warrantyProduct.WarrantyProductEntity;
import com.penguin.esms.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Date;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Audited
public class ProductEntity extends BaseEntity {
    @Column(length=4069)
    private String specifications;
    @Column(unique = true)
    @NotNull
    private String name;
    @ManyToOne
    @NotAudited
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties(value = {"products"})
    private CategoryEntity category;
    @ManyToMany
    @NotAudited
    @JsonIgnore
    @JsonIgnoreProperties(value = {"products"})
    private List<SupplierEntity> suppliers;
    private String unit;
    private Long price;
    private Integer quantity;
    private Integer warrantyPeriod;
    private Boolean isAvailable = true;
    @Column(length=4069)
    private String photoURL;

    public ProductEntity(String id , String name, CategoryEntity category, String unit, Long price, Integer quantity, Integer warrantyPeriod, Boolean isAvailable, String photoURL, String specifications){
        this.setId(id);
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.isAvailable = isAvailable;
        this.photoURL = photoURL;
        this.specifications = specifications;
    }

    @JsonIgnoreProperties(value = {"importProducts"})
    @NotAudited
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<ImportProductEntity> importProducts;
    @JsonIgnoreProperties(value = {"saleProducts"})
    @NotAudited
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<SaleProductEntity> saleProducts;
    @JsonIgnoreProperties(value = {"warrantyProducts"})
    @NotAudited
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<WarrantyProductEntity> warrantyProducts;

}
