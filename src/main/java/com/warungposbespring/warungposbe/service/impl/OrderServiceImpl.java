package com.warungposbespring.warungposbe.service.impl;

import com.warungposbespring.warungposbe.dao.OrderDao;
import com.warungposbespring.warungposbe.dto.OrderResponseDto;
import com.warungposbespring.warungposbe.dto.UserForJwtResponse;
import com.warungposbespring.warungposbe.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Override
    public int getOrderNextService(UserForJwtResponse user) {
        return orderDao.getOrderNextDB(user);
    }

    @Override
    public List<OrderResponseDto> getOrderByOwner(UserForJwtResponse user) {
        return orderDao.getOrderByOwnerDB(user);
    }
}
