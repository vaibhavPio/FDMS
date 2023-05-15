package com.fdms.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private int adminId;
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String gender;
    private String dateOfBirth;
    private String phoneNumber;
    private String role;

    @OneToMany
    private List<OwnerEntity> owner;

    @OneToMany
    private List<RiderEntity> riderEntities;
}
