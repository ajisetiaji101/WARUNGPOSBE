package com.warungposbespring.warungposbe.dto;

import lombok.Builder;

@Builder
public record UserResponse(String email, String password,String username, String name, String foto, String level, String remember_token, String nik) {
}
