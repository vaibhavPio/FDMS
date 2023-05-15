package com.fdms.repository;

import com.fdms.entity.BaseLoginEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BaseLoginRepository extends JpaRepository<BaseLoginEntity,Integer> {

    public BaseLoginEntity findByEmail(String email);

    public boolean existsByEmail(String email);

    @Query(value = "select * from base_login_entity where approved_status=FALSE",nativeQuery = true)
    List<BaseLoginEntity> getPendingRequests();


    @Modifying
    @Transactional
    @Query(value = "delete from base_login_entity where base_login_id=:baseLoginId",nativeQuery = true)
    void rejectApproval(@Param("baseLoginId") int id);









    @Modifying
    @Transactional
    @Query(value = "update base_login_entity set approved_status=1 where username=:userName",nativeQuery = true)
    void giveApproval(@Param("userName") String userName);




}
