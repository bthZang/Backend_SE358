package com.penguin.esms.components.customer;

import com.penguin.esms.components.staff.validators.PhoneNumberFormat;
import com.penguin.esms.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@Audited
public class CustomerEntity extends BaseEntity {
    private String name;
    @Column(unique=true)
    @PhoneNumberFormat(message = "Invalid phone number")
    private String phone;
    private String address;
}
