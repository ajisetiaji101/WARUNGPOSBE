package com.warungposbespring.warungposbe.dao;

import com.warungposbespring.warungposbe.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryDao {
    List<CategoryResponseDto> getAllCategoryDB(String warungId);

    List<CategoryResponseDto> getAllCategoryDBLevel1();
}
