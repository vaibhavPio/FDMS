package com.fdms.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class MyOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int orderId;
    private int restaurantId;
    private String restaurantName;
    private int customerId;
    private String customerName;
    private String customerContactNumber;
    private String status;
    private String dateTime;
    private int qrt;

    @ManyToMany
    List<FoodEntity> foodEntities;

}
