package com.fdms.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class PlaceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int foodId;
    @NotEmpty
    private String foodName;
    @NotNull
    private int foodPrice;
    private int foodQuantity;
    private int totalPrice;
    @NotEmpty
    private String foodDetails;
    private int restaurantId;
    private String restaurantName;
    private Date deliveryDate;
    private String customerName;
    private String customerAddress;
    private String orderStatus;
    private Date date;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="PF_fk")
    private List<FoodEntity> foodEntities;
}
