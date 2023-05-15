package com.fdms.configuration;

import com.fdms.entity.BaseLoginEntity;
import com.fdms.repository.BaseLoginRepository;
import com.fdms.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private BaseLoginRepository baseLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            BaseLoginEntity baseLogin = baseLoginRepository.findByEmail(email);
            String myRole = baseLogin.getRole();
            if (myRole.contains("ROLE_RIDER") || myRole.contains("ROLE_OWNER")) {
                if (baseLogin.isApprovedStatus()) {
                    if (baseLogin != null) {
                        return new CustomUserDetails(baseLogin);
                    }
                }
            } else {
                if (baseLogin != null) {
                    return new CustomUserDetails(baseLogin);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException("User not available");



//        RiderEntity rider = riderRepository.findByEmail(email);
//        if(rider!=null)
//        {
//            return new CustomUserDetails(rider);
//        }
//        throw new UsernameNotFoundException("User not available");


    }
}
