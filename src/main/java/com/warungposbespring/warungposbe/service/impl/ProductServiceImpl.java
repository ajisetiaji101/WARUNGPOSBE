package com.warungposbespring.warungposbe.service.impl;

import com.warungposbespring.warungposbe.dao.ProductDao;
import com.warungposbespring.warungposbe.dto.*;
import com.warungposbespring.warungposbe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public ListResultResponse<ProductDtoResponse> findall(String warungPosIdentity, String page, String size, String sort) {
        return productDao.findAll(warungPosIdentity, page, size, sort);
    }

    @Override
    public void createProduct(ProductDtoRequest request, UserForJwtResponse user) {
        productDao.createProductDB(request, user.getWarung_pos_identity());
    }

    @Override
    public List<ProductDtoResponse> findAllProductByOwner(String warungPosIdentity) {
        return productDao.findAllProductByOwnerDB(warungPosIdentity);
    }

    @Override
    public void cbeckoutProduct(CartDtoCheckoutRequest request, String warungPosIdentity, Integer user_id) {
        productDao.checkoutProductDB(request, warungPosIdentity, user_id);
    }

    @Override
    public List<ProductDtoResponse> findProductByCategoryLevel1(String id, String warungPosIdentity) {
        return productDao.findProductByCategoryLevel1DB(id, warungPosIdentity);
    }

    @Override
    public List<ProductDtoResponse> findProductByCategoryLevel2(String id, String warungPosIdentity) {
        return productDao.findProductByCategoryLevel2DB(id, warungPosIdentity);
    }

    @Override
    public ListResultResponse<ProductDtoResponse> findProductByName(String product, String warungPosIdentity, String page, String size) {
        return productDao.findAllProductNameByOwnerDB(product, warungPosIdentity,page, size);
    }

    @Override
    public ListResultResponse<ProductDtoResponse> findProductByCoordinate(String warungPosIdentity, String page, String size) {
        return productDao.findProductByCoordinateDB(warungPosIdentity, page, size);
    }

}
