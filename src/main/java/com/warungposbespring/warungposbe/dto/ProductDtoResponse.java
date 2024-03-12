package com.warungposbespring.warungposbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class ProductDtoResponse {

    @JsonProperty("id")
    private Integer Id;

    @JsonProperty("serial_number")
    private String SerialNumber;

    @JsonProperty("product_name")
    private String ProductName;

    @JsonProperty("product_price")
    private Integer ProductPrice;

    @JsonProperty("product_image")
    private String ProductImage;

    @JsonProperty("product_sold")
    private Integer ProductSold;

    @JsonProperty("stok")
    private Integer Stok;

    @JsonProperty("product_sold_over")
    private Integer ProductSoldOver;


    @JsonProperty("created_at")
    private Date CreatedAt;

    //orderHistory
    @JsonProperty("qty")
    private Integer qty;


    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lng")
    private Double lng;
}
