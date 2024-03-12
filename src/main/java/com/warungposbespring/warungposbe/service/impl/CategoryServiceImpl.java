package com.warungposbespring.warungposbe.service.impl;

import com.warungposbespring.warungposbe.dao.CategoryDao;
import com.warungposbespring.warungposbe.dto.CategoryResponseDto;
import com.warungposbespring.warungposbe.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<CategoryResponseDto> getAllCategory(String warungId) {
        return categoryDao.getAllCategoryDB(warungId);
    }

    @Override
    public List<CategoryResponseDto> getAllCategoryLevel1() {
        return categoryDao.getAllCategoryDBLevel1();
    }
}
