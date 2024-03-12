package com.warungposbespring.warungposbe.dao.impl;

import com.warungposbespring.warungposbe.dao.UserDao;
import com.warungposbespring.warungposbe.dto.*;
import com.warungposbespring.warungposbe.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    @Qualifier("DBTemplate")
    private JdbcTemplate dataBaseConnection;

    @Override
    public AuthUserResponse findByEmailOrUsername(String email) {


        String sql = "select * from pos_master.m_users mu where mu.email = ?";

        AuthUserResponse result = dataBaseConnection.queryForObject(sql, new Object[]{email}, new RowMapper<AuthUserResponse>() {
            @Override
            public AuthUserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

                UserForJwtResponse dataUser = UserForJwtResponse.builder()
                        .id(rs.getInt("user_id"))
                        .email(rs.getString("email"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .foto(rs.getString("foto"))
                        .level(rs.getString("level"))
                        .warung_pos_identity(rs.getString("warung_pos_identity"))
                        .build();

                AuthUserResponse data = new AuthUserResponse(dataUser);

                data.setEmail(rs.getString("email"));
                data.setPassword(rs.getString("password"));
                data.setUsername(rs.getString("username"));
                data.setName(rs.getString("name"));
                data.setFoto(rs.getString("foto"));
                data.setLevel(rs.getString("level"));
                data.setWarung_pos_identity(rs.getString("warung_pos_identity"));

                return data;
            }
        });

        return result;
    }

    @Override
    public void insertSession(AuthSession authSession) {
        String sql = "insert into pos_master.m_users_session(username, token) values(?,?)";

        Object[] params = {authSession.username(), authSession.token()};

        dataBaseConnection.update(sql, params);
    }

    @Override
    public void deleteSession(String token) {
        String sql = "delete from pos_master.m_users_session where token = ?";
        dataBaseConnection.update(sql, token);
    }

    @Override
    public Boolean findJwtActive(String token) {
        String sql = "select 1 from pos_master.m_users_session where token = ?";

        Map<String, Object> result = dataBaseConnection.queryForObject(sql, new Object[]{token}, new RowMapper<Map<String, Object>>() {
            @Override
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Object> data = new HashMap<>();

                data.put("token", rs.getString("?column?"));
                return data;
            }
        });

        if (result.get("token") != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getSession(String token) {

        String sql = "select count(token) from pos_master.m_users_session where token = ?";

        int data = dataBaseConnection.queryForObject(sql, new Object[]{token}, Integer.class);

        return data;
    }

    @Override
    public void registrasi(RegistrasiDtoRequest request) {
        String sql = "insert into pos_master.m_users(name, username, email, password, nama_warung, alamat, nik)" +
                " values(?,?,?,?,?,?,?)";
        String sqlWarung = "insert into pos_master.m_warung(id_warung, nama_warung, email_user, alamat, lat, lng) values(?,?,?,?,?,?)";
        String sqlGetWarungPosIdentity = "select mp.warung_pos_identity from pos_master.m_users mp where mp.email = ?";

        Object[] params = {
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getNama_warung(),
                request.getAlamat(),
                request.getNik(),
        };
        dataBaseConnection.update(sql, params);

        String idWarung = dataBaseConnection.queryForObject(sqlGetWarungPosIdentity, new Object[]{request.getEmail()}, String.class);


        Object[] paramsWarung = {
                idWarung,
                request.getNama_warung(),
                request.getEmail(),
                request.getAlamat(),
                request.getLat(),
                request.getLng()
        };

        dataBaseConnection.update(sqlWarung, paramsWarung);
    }

    @Override
    public ListResultResponse<UserResponse> findAllUserDB(String page, String size, String sort) {

        int parPage = Integer.parseInt(page);
        int parSize = Integer.parseInt(size);

        int offset = ( parPage - 1 ) * parSize;

        String sql = "select * from pos_master.m_users order by user_id asc limit " + parSize + " offset " + offset;
        String sqlCount = "select count(*) from pos_master.m_users";

        List<Map<String, Object>> rowsGetAllUser = dataBaseConnection.queryForList(sql);

        List<UserResponse> result = new ArrayList<UserResponse>();

        for (Map<String, Object> row : rowsGetAllUser) {
            UserResponse data = new UserResponse(
                    (String) row.get("email"),
                    null,
                    (String) row.get("username"),
                    (String) row.get("name"),
                    (String) row.get("foto"),
                    (String) row.get("level"),
                    null,
                    (String) row.get("nik")
            );
            result.add(data);
        }

        int count = dataBaseConnection.queryForObject(sqlCount, Integer.class);

        int totalPage = PaginationUtils.getPagination(count,parSize);

        Metadata metadata = Metadata.builder()
                .page(parPage)
                .total_items(parSize)
                .total_pages(totalPage)
                .total_items(count)
                .build();

        ListResultResponse<UserResponse> resultResponse = new ListResultResponse<>();

        resultResponse.setData(result);
        resultResponse.setMetadata(metadata);

        return resultResponse;

    }

    @Override
    public ListResultResponse<UserResponse> findByOwnerDB(String warungPosIdentity, String page, String size, String sort) {
        int parPage = Integer.parseInt(page);
        int parSize = Integer.parseInt(size);

        int offset = ( parPage - 1 ) * parSize;

        String sql = "select * from pos_master.m_users where warung_pos_identity = ? order by user_id asc limit " + parSize + " offset " + offset;
        String sqlCount = "select count(*) from pos_master.m_users where warung_pos_identity = ?";

        List<Map<String, Object>> rowsGetAllUser = dataBaseConnection.queryForList(sql, warungPosIdentity);

        List<UserResponse> result = new ArrayList<UserResponse>();

        for (Map<String, Object> row : rowsGetAllUser) {
            UserResponse data = new UserResponse(
                    (String) row.get("email"),
                    null,
                    (String) row.get("username"),
                    (String) row.get("name"),
                    (String) row.get("foto"),
                    (String) row.get("level"),
                    null,
                    (String) row.get("nik")
            );
            result.add(data);
        }

        int count = dataBaseConnection.queryForObject(sqlCount, new Object[]{warungPosIdentity}, Integer.class);

        int totalPage = PaginationUtils.getPagination(count,parSize);

        Metadata metadata = Metadata.builder()
                .page(parPage)
                .total_items(parSize)
                .total_pages(totalPage)
                .total_items(count)
                .build();

        ListResultResponse<UserResponse> resultResponse = new ListResultResponse<>();

        resultResponse.setData(result);
        resultResponse.setMetadata(metadata);

        return resultResponse;
    }

    @Override
    public void createNewUserByOwnerDB(RegistrasiDtoRequest request, String warungPosIdentity) {
        String sql = "insert into pos_master.m_users(name, username, email, password, nama_warung, alamat, nik,level, warung_pos_identity)" +
                " values(?,?,?,?,?,?,?,?,?)";
        String sqlGetWarungName = "select mp.nama_warung from pos_master.m_warung mp where mp.id_warung = ?";

        String warungName = dataBaseConnection.queryForObject(sqlGetWarungName, new Object[]{warungPosIdentity}, String.class);

        Object[] params = {
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                warungName,
                request.getAlamat(),
                request.getNik(),
                request.getLevel().toString(),
                warungPosIdentity
        };
        dataBaseConnection.update(sql, params);
    }
}
