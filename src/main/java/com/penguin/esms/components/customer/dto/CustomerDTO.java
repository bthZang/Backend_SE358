package com.penguin.esms.components.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String name;
    private String phone;
    private String address;
}
