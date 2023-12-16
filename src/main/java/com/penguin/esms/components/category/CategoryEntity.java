package com.penguin.esms.components.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.penguin.esms.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.penguin.esms.components.product.ProductEntity;


import java.util.List;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
public class CategoryEntity extends BaseEntity {
    private String name;
    @JsonIgnoreProperties(value = {"category"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<ProductEntity> products;

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
