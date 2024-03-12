package com.warungposbespring.warungposbe.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponseDto {
    private Integer order_id;
    private String warung_id;
    private Integer user_id;
    private String user_name;
    private Integer order_total;
    private String invoice;
    private Integer discount;
    private Integer pajak;
    private Integer qty;
    private Integer order_price;
    private List<ProductDtoResponse> list_product;

}
