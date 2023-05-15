package com.fdms.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseLoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int baseLoginId;
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean approvedStatus;

}
