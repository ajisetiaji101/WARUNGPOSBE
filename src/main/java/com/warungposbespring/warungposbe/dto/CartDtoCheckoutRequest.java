package com.warungposbespring.warungposbe.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDtoCheckoutRequest {
    private String invoice;
    private Integer total_price;
    private Integer pajak;
    private Integer discount;
    private Integer qtyCart;
    private List<ProductDtoCheckoutRequest> list_item;
}
