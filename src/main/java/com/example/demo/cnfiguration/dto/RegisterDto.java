package com.example.demo.cnfiguration.dto;

import com.example.demo.entity.Address;
import com.example.demo.entity.Role;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class RegisterDto {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Long addressId;


    private Set<Long> roleSetId=new HashSet<>();
}
