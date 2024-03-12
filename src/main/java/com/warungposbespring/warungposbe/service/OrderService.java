package com.warungposbespring.warungposbe.service;

import com.warungposbespring.warungposbe.dto.OrderResponseDto;
import com.warungposbespring.warungposbe.dto.UserForJwtResponse;

import java.util.List;

public interface OrderService {
    int getOrderNextService(UserForJwtResponse user);

    List<OrderResponseDto> getOrderByOwner(UserForJwtResponse user);
}
