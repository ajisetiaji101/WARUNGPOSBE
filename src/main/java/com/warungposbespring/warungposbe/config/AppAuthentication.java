package com.warungposbespring.warungposbe.config;

import com.warungposbespring.warungposbe.dto.UserForJwtResponse;
import com.warungposbespring.warungposbe.enums.AppSource;
import com.warungposbespring.warungposbe.dto.AuthUserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class AppAuthentication implements Authentication {

    private final UserForJwtResponse user;
    private boolean authenticated;

    private final AppSource appSource;

    public AppAuthentication(UserForJwtResponse user, AppSource appSource) {
        this.user = user;
        this.authenticated = true;
        this.appSource = appSource;

        if (this.user.getRoles() == null) {
            this.user.setRoles(Collections.emptyList());
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(val -> {
           return (GrantedAuthority) val::name;
        }).collect(Collectors.toList());
    }

    @Override
    public String getCredentials() {
        return user.getWarung_pos_identity();
    }

    @Override
    public UserForJwtResponse getDetails() {
        return this.user;
    }

    @Override
    public String getPrincipal() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean value){
        this.authenticated = value;
    }

    @Override
    public String getName() {
        return this.user.getName();
    }

    public AppSource getAppSource(){
        return  this.appSource;
    }
}
