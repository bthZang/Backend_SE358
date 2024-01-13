package com.penguin.esms.components.saleBill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.penguin.esms.components.customer.CustomerEntity;
import com.penguin.esms.entity.NoteEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@Audited
public class SaleBillEntity extends NoteEntity {
    private String staffId;
    private String paymentMethod;
    private Float discount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties(value = {"saleBills"})
    private CustomerEntity customer;

}
