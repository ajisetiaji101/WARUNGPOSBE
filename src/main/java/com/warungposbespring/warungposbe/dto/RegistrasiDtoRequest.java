package com.warungposbespring.warungposbe.dto;

import com.warungposbespring.warungposbe.enums.UserRole;
import lombok.Data;

@Data
public class RegistrasiDtoRequest {
    private String email;
    private String password;
    private String username;
    private String nama_warung;
    private String name;
    private String nik;
    private String alamat;
    private UserRole level;
    private Float lat;
    private Float lng;
}
