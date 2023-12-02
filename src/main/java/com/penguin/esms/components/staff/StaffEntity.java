package com.penguin.esms.components.staff;

import com.penguin.esms.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
@Setter
@Entity
@Table
public class StaffEntity extends BaseEntity implements UserDetails {
    @Pattern(regexp = "^[a-z0-9A-Z_àáãạảăắằẳẵặâấầẩẫậèéẹẻẽêềếểễệđìíĩỉịòóõọỏôốồổỗộơớờởỡợùúũụủưứừửữựỳỵỷỹýÀÁÃẠẢĂẮẰẲẴẶÂẤẦẨẪẬÈÉẸẺẼÊỀẾỂỄỆĐÌÍĨỈỊÒÓÕỌỎÔỐỒỔỖỘƠỚỜỞỠỢÙÚŨỤỦƯỨỪỬỮỰỲỴỶỸÝ ]*$", message = "Name should not contain special characters")
    @NotNull
    private String name;
    @Size(min = 10, message = "Phone number must be at least 1 characters long")
    private String phone;
    @Size(min = 1, message = "Password must be at least 1 characters long")
    private String password;
    @Column(unique = true)
    @Email(message = "Invalid email address format")
    private String email;
    @Column(unique = true)
    @Size.List({
            @Size(min = 12, max = 12, message = "Citizen ID must be 12 characters long")
    })
    private String citizenId;
    private String role;

    StaffEntity(){

    }

    public StaffEntity(String name, String phone, String password, String email, String citizenId, String role) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.citizenId = citizenId;
        this.role = role;
    }

    @Override
    public String toString() {
        return "StaffEntity{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", citizenId='" + citizenId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
