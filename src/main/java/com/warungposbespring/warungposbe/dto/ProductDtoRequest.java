package com.warungposbespring.warungposbe.dto;

import lombok.Data;

@Data
public class ProductDtoRequest {
    private String serial_number;
    private String product_name;
    private Integer product_price;
    private String product_image;
    private Integer product_category_1;
    private Integer product_category_2;
}
