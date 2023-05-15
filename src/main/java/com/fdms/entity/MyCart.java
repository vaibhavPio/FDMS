package com.fdms.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyCart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int cartId;
    private String restaurantName;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name ="Cr-Fo_fk")
//    private List<FoodEntity> foodEntities;


}
