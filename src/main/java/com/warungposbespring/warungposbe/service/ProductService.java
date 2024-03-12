package com.warungposbespring.warungposbe.service;

import com.warungposbespring.warungposbe.dto.*;

import java.util.List;

public interface ProductService {
    ListResultResponse<ProductDtoResponse> findall(String warungPosIdentity, String page, String size, String sort);

    void createProduct(ProductDtoRequest request, UserForJwtResponse user);

    List<ProductDtoResponse> findAllProductByOwner(String warungPosIdentity);

    void cbeckoutProduct(CartDtoCheckoutRequest request, String warungPosIdentity, Integer user_id);

    List<ProductDtoResponse> findProductByCategoryLevel1(String id, String warungPosIdentity);

    List<ProductDtoResponse> findProductByCategoryLevel2(String id, String warungPosIdentity);

    ListResultResponse<ProductDtoResponse> findProductByName(String product, String warungPosIdentity, String page, String size);

    ListResultResponse<ProductDtoResponse> findProductByCoordinate(String warungPosIdentity, String page, String size);
}
