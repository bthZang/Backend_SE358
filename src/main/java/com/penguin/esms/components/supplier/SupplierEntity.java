package com.penguin.esms.components.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.penguin.esms.components.product.ProductEntity;
import com.penguin.esms.entity.NoteEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class SupplierEntity extends NoteEntity{
    private String name;
    private String phone;
    private String email;
    private String address;

    @NotAudited
    @JsonIgnoreProperties(value = {"suppliers"})
    @ManyToMany(mappedBy = "suppliers")
    @JsonIgnore
    private List<ProductEntity> products;
}
