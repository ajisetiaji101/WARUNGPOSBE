package com.warungposbespring.warungposbe.dao;

import com.warungposbespring.warungposbe.dto.ListResultResponse;
import com.warungposbespring.warungposbe.dto.WarungResponseDto;

public interface WarungDao {
    ListResultResponse<WarungResponseDto> findAllWarungDB(String page, String size, String sort);
}
