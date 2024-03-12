package com.warungposbespring.warungposbe.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Metadata {
    private int total_items;
    private int page;
    private int perpage;
    private int total_pages;
}
