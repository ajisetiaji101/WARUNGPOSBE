package com.warungposbespring.warungposbe.service.impl;

import com.warungposbespring.warungposbe.dao.UserDao;
import com.warungposbespring.warungposbe.dto.AuthUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthUserResponse user = userDao.findByEmailOrUsername(username);

//        List<String> roles = new ArrayList<>();
//        roles.add("USER");
//
//        UserDetails userDetails = User.builder()
//                .username(user.username())
//                .password("$2a$12$KfCLgJsaQhULrjCkMBTUt.464SL5m5wTXlGHawSJoGh2tZmZ1V992")
//                .roles(roles.toArray(new String[0]))
//                .accountLocked(false)
//                .accountExpired(false)
//                .credentialsExpired(false)
//                .build();

        return user;
    }
}
