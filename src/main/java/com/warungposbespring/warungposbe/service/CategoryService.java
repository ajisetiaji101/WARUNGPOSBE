package com.warungposbespring.warungposbe.service;

import com.warungposbespring.warungposbe.dto.CategoryResponseDto;

import java.util.List;


public interface CategoryService {

    List<CategoryResponseDto> getAllCategory(String warungId);

    List<CategoryResponseDto> getAllCategoryLevel1();
}
