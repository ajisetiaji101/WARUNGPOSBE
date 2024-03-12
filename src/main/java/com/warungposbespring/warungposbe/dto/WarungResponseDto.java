package com.warungposbespring.warungposbe.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarungResponseDto {
    private String id_warung;
    private String nama_warung;
    private String alamat;
    private String telephone;
    private String path_logo;
    private Double lat;
    private Double lng;
    private Boolean verified;
    private String email_user;
}
