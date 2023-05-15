package com.fdms.repository;


import com.fdms.entity.RiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RiderRepository extends JpaRepository<RiderEntity,Integer> {

    boolean existsByEmail(String email);

    @Query(value = "select * from rider_entity where approved_status=TRUE",nativeQuery = true)
    List<RiderEntity> findAllApproved();


     RiderEntity findByEmail(String email);



}
