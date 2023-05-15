package com.fdms.repository;


import com.fdms.entity.PlaceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PlaceOrderRepository extends JpaRepository<PlaceOrder,Integer> {

    @Query(value = "SELECT * FROM place_order where restaurant_id =:id",nativeQuery = true)
    List<PlaceOrder> findAllMyOrder(@Param("id") int id);

    @Query(value = "SELECT * FROM place_order where cp_fk =:id",nativeQuery = true)
    List<PlaceOrder> findAllOrder(@Param("id") int id);

//    @Query(value = "SELECT * FROM place_order where cp_fk =:id",nativeQuery = true)
//    void findAllOwnerOrder(int restaurantId);
}
