package com.warungposbespring.warungposbe.service;

import com.warungposbespring.warungposbe.dto.ListResultResponse;
import com.warungposbespring.warungposbe.dto.WarungResponseDto;

public interface WarungService {
    ListResultResponse<WarungResponseDto> findAllWarung(String page, String size, String sort);
}
