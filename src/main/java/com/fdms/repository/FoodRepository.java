package com.fdms.repository;

import com.fdms.entity.FoodEntity;
import com.fdms.entity.OwnerEntity;
import com.fdms.entity.PlaceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity,Integer> {
    boolean existsByFoodName(String foodName);


//    @Query(value = "SELECT * FROM food_entity where fr_fk =:id",nativeQuery = true)
//    FoodEntity findOwner(@Param("id") int id);


    @Query(value = "SELECT fr_fk FROM food_entity where food_id =:id",nativeQuery = true)
    Integer findOwner(@Param("id") int id);


}
