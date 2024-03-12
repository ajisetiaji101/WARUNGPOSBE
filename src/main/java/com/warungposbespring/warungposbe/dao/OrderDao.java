package com.warungposbespring.warungposbe.dao;

import com.warungposbespring.warungposbe.dto.OrderResponseDto;
import com.warungposbespring.warungposbe.dto.UserForJwtResponse;

import java.util.List;

public interface OrderDao {
    int getOrderNextDB(UserForJwtResponse user);

    List<OrderResponseDto> getOrderByOwnerDB(UserForJwtResponse user);
}
