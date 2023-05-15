package com.fdms.repository;

import com.fdms.entity.ConfirmationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity,Integer> {

    ConfirmationTokenEntity findByConfirmationToken(String confirmationToken);

}
