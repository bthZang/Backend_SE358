package com.penguin.esms.components.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.penguin.esms.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.penguin.esms.components.product.ProductEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;


import java.util.List;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@Audited
public class CategoryEntity extends BaseEntity {
    private String name;
    @JsonIgnoreProperties(value = {"category"})
    @NotAudited
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<ProductEntity> products;

    public CategoryEntity(String id , String name) {
        this.setId(id);
        this.name = name;
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
