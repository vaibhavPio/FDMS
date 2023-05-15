package com.fdms.repository;

import com.fdms.entity.AdminEntity;
import com.fdms.entity.BaseLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<AdminEntity,Integer> {

     boolean existsByEmail(String email);



     AdminEntity findByEmail(String email);
}
