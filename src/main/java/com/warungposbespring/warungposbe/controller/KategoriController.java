package com.warungposbespring.warungposbe.controller;

import com.warungposbespring.warungposbe.dto.CategoryResponseDto;
import com.warungposbespring.warungposbe.service.CategoryService;
import com.warungposbespring.warungposbe.utils.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("*")
public class KategoriController {

    private final CategoryService categoryService;

    public KategoriController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getCategoryAll(@RequestParam(required = false) String warungId){

        List<CategoryResponseDto> listCategory = categoryService.getAllCategory(warungId);

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, listCategory, true);
    }

    @GetMapping("/findall")
    public ResponseEntity<?> getCategoryAllLevel1(){

        List<CategoryResponseDto> list = categoryService.getAllCategoryLevel1();

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, list, true);

    }

}
