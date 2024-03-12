package com.warungposbespring.warungposbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryResponseDto {
    @JsonProperty("id")
    private Integer Id;

    @JsonProperty("category_name")
    private String CategoryName;

    @JsonProperty("category_reference")
    private Integer CategoyReference;

    @JsonProperty("sub_category")
    private List<CategoryResponseDto> ListSubCategory;
}
