package com.warungposbespring.warungposbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDtoCheckoutRequest {
    @JsonProperty("id")
    private Integer Id;

    @JsonProperty("qtyItem")
    private Integer QtyItem;
}
