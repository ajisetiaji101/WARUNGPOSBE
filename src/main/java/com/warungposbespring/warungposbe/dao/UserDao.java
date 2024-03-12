package com.warungposbespring.warungposbe.dao;

import com.warungposbespring.warungposbe.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    AuthUserResponse findByEmailOrUsername(String email);

    void insertSession(AuthSession authSession);

    void deleteSession(String token);

    Boolean findJwtActive(String token);

    int getSession(String token);

    void registrasi(RegistrasiDtoRequest request);

    ListResultResponse<UserResponse> findAllUserDB(String page, String size, String sort);

    ListResultResponse<UserResponse> findByOwnerDB(String warungPosIdentity, String page, String size, String sort);

    void createNewUserByOwnerDB(RegistrasiDtoRequest request, String warungPosIdentity);
}
