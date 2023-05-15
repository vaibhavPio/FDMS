package com.fdms.repository;

import com.fdms.entity.CustomerEntity;
import com.fdms.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer> {

    CustomerEntity findByEmailIgnoreCase(String email);



}
