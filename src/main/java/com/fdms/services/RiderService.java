package com.fdms.services;

import com.fdms.dto.RiderEntityDto;
import com.fdms.entity.RiderEntity;

public interface RiderService {

   RiderEntity createRider(RiderEntity rider);

    boolean checkEmail(String email);

    RiderEntity updateRiderInfo(String username);

    void riderEdit(RiderEntityDto riderRequestDto);
}
