package com.warungposbespring.warungposbe.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListResultResponse<T> {
    private Metadata metadata;
    private List<T> data;
}
