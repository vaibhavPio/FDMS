package com.fdms.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int foodId;
    @NotEmpty
    private String foodName;
    @NotNull
    private int foodPrice;
    private int foodQuantity;
    private int totalPrice;
    @NotEmpty
    private String foodDetails;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="FR_FK")
    private OwnerEntity owner;
}
