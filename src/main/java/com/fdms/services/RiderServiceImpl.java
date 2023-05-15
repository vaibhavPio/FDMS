package com.fdms.services;

import com.fdms.dto.RiderEntityDto;
import com.fdms.entity.BaseLoginEntity;
import com.fdms.entity.RiderEntity;
import com.fdms.repository.BaseLoginRepository;
import com.fdms.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    private BaseLoginRepository baseLoginRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public RiderEntity createRider(RiderEntity rider) {

        BaseLoginEntity baseLogin = new BaseLoginEntity();
        rider.setPassword(passwordEncoder.encode(rider.getPassword()));
        rider.setRole("ROLE_RIDER");
        baseLogin.setPassword(rider.getPassword());
        baseLogin.setRole(rider.getRole());
        baseLogin.setName(rider.getName());
        baseLogin.setEmail(rider.getEmail());
        baseLoginRepository.save(baseLogin);
        return riderRepository.save(rider);


    }
    @Override
    public boolean checkEmail(String email) {
        return riderRepository.existsByEmail(email);
    }

    @Override
    public RiderEntity updateRiderInfo(String username) {

        return riderRepository.findByEmail(username);
    }

    @Override
    public void riderEdit(RiderEntityDto riderRequestDto) {

        RiderEntity rider = new RiderEntity();
        rider.setRiderId(riderRequestDto.getRiderId());
        rider.setActivationStatus(riderRequestDto.isActivationStatus());
        rider.setRole(riderRequestDto.getRole());
        rider.setPassword(riderRequestDto.getPassword());
        rider.setEmail(riderRequestDto.getEmail());
        rider.setAddress(riderRequestDto.getAddress());
        rider.setDateOfBirth(riderRequestDto.getDateOfBirth());
        rider.setGender(riderRequestDto.getGender());
        rider.setName(riderRequestDto.getName());
        rider.setPhoneNumber(riderRequestDto.getPhoneNumber());
        rider.setActivationStatus(riderRequestDto.isActivationStatus());

        riderRepository.save(rider);
    }


}
