package com.warungposbespring.warungposbe.dto;

import com.warungposbespring.warungposbe.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForJwtResponse {
    private Integer id;
    private String email;
    private String password;
    private String username;
    private String name;
    private String foto;
    private String level;
    private String remember_token;
    private String warung_pos_identity;
    private List<UserRole> roles;
}
