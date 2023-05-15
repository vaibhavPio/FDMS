package com.fdms.configuration;

import com.fdms.entity.BaseLoginEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private BaseLoginEntity baseLogin;

//    public CustomUserDetails(RiderEntity rider) {
//        this.rider = rider; }

    public CustomUserDetails(BaseLoginEntity baseLogin) {
        this.baseLogin = baseLogin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(baseLogin.getRole());
        return Arrays.asList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return baseLogin.getPassword();
    }

    @Override
    public String getUsername() {
        return baseLogin.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
