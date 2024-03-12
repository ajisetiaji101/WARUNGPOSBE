package com.warungposbespring.warungposbe.service;

import com.warungposbespring.warungposbe.dto.*;

public interface UserService {

    LoginDtoResponse authentication(LoginDtoRequest request);

    Boolean getTokenJwt();

    void logoutSession(String token);

    void sessionLogin(String token);

    void registrasi(RegistrasiDtoRequest request);

    ListResultResponse<UserResponse> findAllUser(String page, String size, String sort);

    ListResultResponse<UserResponse> findByOwner(String warungPosIdentity, String page, String size, String sort);

    void createNewUserByOwner(RegistrasiDtoRequest request, String warungPosIdentity);
}
