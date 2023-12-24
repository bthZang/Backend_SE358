package com.penguin.esms.components.authenticate;

import com.penguin.esms.components.staff.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String phone;
    private String password;
    private String email;
    private String citizenId;
    private Role role;
}