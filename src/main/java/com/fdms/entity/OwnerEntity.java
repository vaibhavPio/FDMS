package com.fdms.entity;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private int restaurantId;
    @NotEmpty
    private String ownerName;
    @NotEmpty
    private String gender;
    @Email
    private String email;
    private String phoneNumber;
    private String restaurantName;
    private String restaurantAddress;
    private String role;
    @NotEmpty
    private String password;
    private boolean approvedStatus;
    private boolean activationStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="restaurantId")
    private List<FoodEntity> foodEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="Ow_Po")
    private List<PlaceOrder> placeOrders;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="OR_fk")
    private List<RequestAndSuggestion> requestAndComplaints;
}

