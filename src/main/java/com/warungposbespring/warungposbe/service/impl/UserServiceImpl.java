package com.warungposbespring.warungposbe.service.impl;

import com.warungposbespring.warungposbe.dao.UserDao;
import com.warungposbespring.warungposbe.dto.*;
import com.warungposbespring.warungposbe.service.UserService;
import com.warungposbespring.warungposbe.utils.AppException;
import com.warungposbespring.warungposbe.utils.JwtUtil;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginDtoResponse authentication(LoginDtoRequest request) {

        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (AuthenticationException e){
            throw AppException.create("Gagal", HttpStatus.FORBIDDEN.value(), "Periksa kembali username dan password Anda !");
        }

        if (authentication.isAuthenticated()) {

            AuthUserResponse user = userDao.findByEmailOrUsername(request.email());

//            AppAuthentication appAuthentication = new AppAuthentication(user, AppSource.FO);

            String token = jwtUtil.createToken(user);

            AuthSession session = new AuthSession(user.getUsername(), token);

            userDao.insertSession(session);

            LoginDtoResponse result = new LoginDtoResponse(token);

            return result;
        } else {
            throw new UsernameNotFoundException(" Invalid user Request ");
        }
    }

    @Override
    public Boolean getTokenJwt() {
        return null;
    }

    @Override
    public void logoutSession(String token) {
        userDao.deleteSession(token);
    }

    @Override
    public void sessionLogin(String token) {
        int data = userDao.getSession(token);

        if(data == 0){
            throw new AppException("Anda telah logout");
        }
    }

    @Override
    public void registrasi(@NotNull RegistrasiDtoRequest request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        userDao.registrasi(request);
    }

    @Override
    public ListResultResponse<UserResponse> findAllUser(String page, String size, String sort) {
        return userDao.findAllUserDB(page, size, sort);
    }

    @Override
    public ListResultResponse<UserResponse> findByOwner(String warungPosIdentity, String page, String size, String sort) {
        return userDao.findByOwnerDB(warungPosIdentity, page, size, sort);
    }

    @Override
    public void createNewUserByOwner(RegistrasiDtoRequest request, String warungPosIdentity) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        userDao.createNewUserByOwnerDB(request, warungPosIdentity);
    }
}
