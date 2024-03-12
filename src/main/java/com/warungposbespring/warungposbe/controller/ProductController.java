package com.warungposbespring.warungposbe.controller;


import com.warungposbespring.warungposbe.dto.*;
import com.warungposbespring.warungposbe.service.ProductService;
import com.warungposbespring.warungposbe.utils.HttpResponse;
import com.warungposbespring.warungposbe.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/findall")
    public ResponseEntity<?> getAllProduct(
            @Parameter(name = "page", example = "1", required = false) @RequestParam String page,
            @Parameter(name = "size", example = "10", required = false) @RequestParam String size,
            @Parameter(name = "sort", example = "createdAt, desc", required = true) @RequestParam(defaultValue = "desc") String sort

    ){

        UserForJwtResponse user = SecurityUtil.getAuthUser();

        ListResultResponse<ProductDtoResponse> resultProduct = productService.findall(user.getWarung_pos_identity(), page, size, sort);

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, resultProduct, true);
    }

    @GetMapping("findAllyOwner/{id}")
    public ResponseEntity<?> getAllProductByOwner(@PathVariable String id){
        UserForJwtResponse user = SecurityUtil.getAuthUser();

        List<ProductDtoResponse> resultProductOwner= productService.findAllProductByOwner(user.getWarung_pos_identity());
        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, resultProductOwner, true);
    };

    @GetMapping("findProductByCategoryLevel1")
    public ResponseEntity<?> getProductByCategory(@RequestParam("id") String id){
        UserForJwtResponse user = SecurityUtil.getAuthUser();

        List<ProductDtoResponse> resultProductOwner= productService.findProductByCategoryLevel1(id, user.getWarung_pos_identity());
        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, resultProductOwner, true);
    };

    @GetMapping("findProductByCategoryLevel2")
    public ResponseEntity<?> getProductByCategoryLvl2(@RequestParam("id") String id){
        UserForJwtResponse user = SecurityUtil.getAuthUser();

        List<ProductDtoResponse> resultProductOwner= productService.findProductByCategoryLevel2(id, user.getWarung_pos_identity());
        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, resultProductOwner, true);
    };

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductDtoRequest request){

        UserForJwtResponse user = SecurityUtil.getAuthUser();
        productService.createProduct(request, user);
        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, "sukses", true);
    };

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutProduct(@RequestBody CartDtoCheckoutRequest request){
        UserForJwtResponse user = SecurityUtil.getAuthUser();
        productService.cbeckoutProduct(request, user.getWarung_pos_identity(), user.getId());
        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, "sukses", true);
    };

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(
            @RequestParam("product") String product,
            @Parameter(name = "page", example = "1", required = false) @RequestParam String page,
            @Parameter(name = "size", example = "10", required = false) @RequestParam String size
            ){
        UserForJwtResponse user = SecurityUtil.getAuthUser();

        ListResultResponse<ProductDtoResponse> resultProductOwner= productService.findProductByName(product, user.getWarung_pos_identity(), page, size);

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, resultProductOwner, true);
    };

    @GetMapping("/getProductByCoordinate")
    public ResponseEntity<?> getProductByCoordinate(
            @Parameter(name = "page", example = "1", required = false) @RequestParam String page,
            @Parameter(name = "size", example = "10", required = false) @RequestParam String size,
            @Parameter(name = "sort", example = "createdAt, desc", required = true) @RequestParam(defaultValue = "desc") String sort
    ){
        UserForJwtResponse user = SecurityUtil.getAuthUser();

        ListResultResponse<ProductDtoResponse> resultProductOwner= productService.findProductByCoordinate(user.getWarung_pos_identity(), page, size);

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, resultProductOwner, true);
    };




}
