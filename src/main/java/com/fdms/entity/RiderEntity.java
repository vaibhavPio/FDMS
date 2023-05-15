package com.fdms.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RiderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int riderId;


    @Size(min = 3,max = 50, message = "Name length should be in between 3 to 50 ")
    private String name;

    @NotBlank
    @Email(message = "Please enter a valid e-mail address")
    private String email;

    @NotBlank
    private String password;
    @NotBlank
    private String gender;

    @Size(max = 10, message = "digits should be 10")
    private String phoneNumber;

    private String  dateOfBirth;

    @NotBlank
    private String address;

    private String role;

    private boolean approvedStatus;

    private boolean activationStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="RR_fk")
    private List<RequestAndSuggestion> requestAndComplaints;




}
