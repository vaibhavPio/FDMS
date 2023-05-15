package com.fdms.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RiderEntityDto {

    private int riderId;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String phoneNumber;
    private String  dateOfBirth;
    private String address;
    private String role;
    private boolean approvedStatus;
    private boolean activationStatus;
}
