package com.warungposbespring.warungposbe.dto;

import lombok.Data;

@Data
public class OrderDetailResponseDto {
    private Integer order_details_id;
    private Integer order_id;
    private Integer product_id;
    private Integer qty;
    private Integer price;
}
