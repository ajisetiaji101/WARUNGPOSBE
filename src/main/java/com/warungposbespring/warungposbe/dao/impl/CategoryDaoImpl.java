package com.warungposbespring.warungposbe.dao.impl;

import com.warungposbespring.warungposbe.dao.CategoryDao;
import com.warungposbespring.warungposbe.dto.CategoryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    @Qualifier("DBTemplate")
    private JdbcTemplate dataBaseConnection;

    @Override
    public List<CategoryResponseDto> getAllCategoryDB(String warungId) {

        String sqlListCategory = "select distinct(category_level_1) from pos_master.m_product where warung_pos_identity = ?";
        String sqlListSubCateoryMain = "select * from pos_master.m_kategori where id = ?";
        String sqlFindSameFromCategory = " select distinct(category_level_2) from pos_master.m_product where category_level_1 = ? and warung_pos_identity = ?";


        List<Map<String, Object>> listCategoryMain = dataBaseConnection.queryForList(sqlListCategory, warungId);

        List<CategoryResponseDto> dataCategory = new ArrayList<>();

        listCategoryMain.stream().forEach(val -> {

            List<Map<String, Object>> listSubCategoryMain = dataBaseConnection.queryForList(sqlFindSameFromCategory, new Object[] {val.get("category_level_1"), warungId});

            List<CategoryResponseDto> dataSubCategory = new ArrayList<>();

            listSubCategoryMain.forEach(sub -> {
                    CategoryResponseDto subCategory = dataBaseConnection.queryForObject(sqlListSubCateoryMain, new Object[]{sub.get("category_level_2")},new RowMapper<CategoryResponseDto>() {
                        @Override
                        public CategoryResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                            CategoryResponseDto dataMap = CategoryResponseDto.builder()
                                    .Id(rs.getInt("id"))
                                    .CategoryName(rs.getString("category_name"))
                                    .CategoyReference(rs.getInt("category_reference"))
                                    .build();

                            return dataMap;
                        }
                    });

                    dataSubCategory.add(subCategory);
            });


            CategoryResponseDto category = dataBaseConnection.queryForObject(sqlListSubCateoryMain, new Object[]{val.get("category_level_1")},new RowMapper<CategoryResponseDto>() {
                @Override
                public CategoryResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                    CategoryResponseDto dataMap = CategoryResponseDto.builder()
                            .Id(rs.getInt("id"))
                            .CategoryName(rs.getString("category_name"))
                            .CategoyReference(rs.getInt("category_reference"))
                            .ListSubCategory(dataSubCategory)
                            .build();

                    return dataMap;
                }
            });

            dataCategory.add(category);
        });


        return dataCategory;
    }

    @Override
    public List<CategoryResponseDto> getAllCategoryDBLevel1() {

        String sql = "select * from pos_master.m_kategori where category_reference is null order by id asc";
        String sqlSubCategory = "select * from pos_master.m_kategori where category_reference = ? order by id asc";

        List<Map<String, Object>> result = dataBaseConnection.queryForList(sql);

        List<CategoryResponseDto> categoryMain = new ArrayList<>();

        result.stream().forEach(val -> {

            List<CategoryResponseDto> dataSubCategory = new ArrayList<>();
            List<Map<String, Object>> listSubCategoryMain = dataBaseConnection.queryForList(sqlSubCategory, new Object[] {val.get("id")});

            for(Map<String,Object> row : listSubCategoryMain){
                CategoryResponseDto categoryResponseDto = CategoryResponseDto.builder()
                        .Id((Integer) row.get("id"))
                        .CategoryName((String) row.get("category_name"))
                        .CategoyReference((Integer) row.get("category_reference"))
                        .build();

                dataSubCategory.add(categoryResponseDto);
            }

            CategoryResponseDto categoryLvl1 = CategoryResponseDto.builder()
                    .Id((Integer) val.get("id"))
                    .CategoryName((String) val.get("category_name"))
                    .ListSubCategory(dataSubCategory)
                    .build();

            categoryMain.add(categoryLvl1);
        });

        return categoryMain;
    }
}
