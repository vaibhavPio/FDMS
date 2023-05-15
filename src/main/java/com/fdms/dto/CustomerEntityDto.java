package com.fdms.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerEntityDto {

    private int customerId;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String  phoneNumber;
    private String role;
    private String  dateOfBirth;
    private String address;
    private int totalOrder;
    private boolean isEnabled;
}
