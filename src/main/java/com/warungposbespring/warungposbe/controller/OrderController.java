package com.warungposbespring.warungposbe.controller;

import com.warungposbespring.warungposbe.dto.CategoryResponseDto;
import com.warungposbespring.warungposbe.dto.OrderResponseDto;
import com.warungposbespring.warungposbe.dto.UserForJwtResponse;
import com.warungposbespring.warungposbe.service.OrderService;
import com.warungposbespring.warungposbe.utils.HttpResponse;
import com.warungposbespring.warungposbe.utils.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getOrderByOwner")
    public ResponseEntity<?> getOrderByOwner(){

        UserForJwtResponse user = SecurityUtil.getAuthUser();

        List<OrderResponseDto> resultOrder = orderService.getOrderByOwner(user);

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, resultOrder, true);
    };

    @GetMapping("/getOrderNext")
    public ResponseEntity<?> getOrderNext(){

        UserForJwtResponse user = SecurityUtil.getAuthUser();

        int resultOrder = orderService.getOrderNextService(user);

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, resultOrder, true);
    };
}
