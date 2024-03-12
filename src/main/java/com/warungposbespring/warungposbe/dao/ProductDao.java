package com.warungposbespring.warungposbe.dao;

import com.warungposbespring.warungposbe.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
    ListResultResponse<ProductDtoResponse> findAll(String warungPosIdentity, String page, String size, String sort);

    void createProductDB(ProductDtoRequest request, String warungPosIdentity);

    List<ProductDtoResponse> findAllProductByOwnerDB(String warungPosIdentity);

    void checkoutProductDB(CartDtoCheckoutRequest request, String warungPosIdentity, Integer user_id);

    List<ProductDtoResponse> findProductByCategoryLevel1DB(String id, String warungPosIdentity);

    List<ProductDtoResponse> findProductByCategoryLevel2DB(String id, String warungPosIdentity);

    ListResultResponse<ProductDtoResponse> findAllProductNameByOwnerDB(String product, String warungPosIdentity, String page, String size);

    ListResultResponse<ProductDtoResponse> findProductByCoordinateDB(String warungPosIdentity, String page, String size);
}
