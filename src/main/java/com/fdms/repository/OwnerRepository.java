package com.fdms.repository;

import com.fdms.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerRepository extends JpaRepository<OwnerEntity,Integer> {

     boolean existsByEmail(String email);

    @Query(value = "select * from owner_entity where approved_status=TRUE",nativeQuery = true)
    List<OwnerEntity> findAllApproved();

     OwnerEntity findByEmail(String email);

    @Query(value = "select * from owner_entity where activation_status=TRUE",nativeQuery = true)
    List<OwnerEntity> findAllActivate();
}
