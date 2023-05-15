package com.fdms.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name="customer_id")
    private int customerId;

    @NotEmpty
    private String name;

    @Email(message = "Email address is not valid.!!")
    private String email;

    @NotEmpty
    @Size(min = 3, message = "Password must be min of 3 chars !!")
    private String password;

    @NotEmpty
    private String gender;

    @NotNull
    @Size(min = 10,max = 10)
    private String  phoneNumber;

    private String role;

    @NotNull
    private String  dateOfBirth;

    @NotEmpty
    private String address;
    private int totalOrder;
    private boolean isEnabled;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="CO_fk")
    private List<OwnerEntity> ownerEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="CP_fk")
    private List<PlaceOrder> placeOrders;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="CR_fk")
    private List<RequestAndSuggestion> requestAndComplaints;
}
