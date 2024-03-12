package com.warungposbespring.warungposbe.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warungposbespring.warungposbe.dao.ProductDao;
import com.warungposbespring.warungposbe.dto.*;
import com.warungposbespring.warungposbe.utils.PaginationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ProductDaoImpl implements ProductDao {

    @Autowired
    @Qualifier("DBTemplate")
    private JdbcTemplate dataBaseConnection;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataSource dataSource;

    @Override
    public ListResultResponse<ProductDtoResponse> findAll(String warungPosIdentity, String page, String size, String sort) {

        int parPage = Integer.parseInt(page);
        int parSize = Integer.parseInt(size);

        int offset = ( parPage - 1 ) * parSize;

        String sql = "SELECT * FROM pos_master.product_findall(?, ?, ?)";

        Object[] params = {
          warungPosIdentity,
          offset,
          parSize
        };

        List<Map<String, Object>> resultData = dataBaseConnection.queryForList(sql, params);

        List<ProductDtoResponse> resultList = new ArrayList<>();

        for (Map<String, Object> row : resultData) {

            ProductDtoResponse productDtoResponse = new ProductDtoResponse();

            productDtoResponse.setId((Integer) row.get("product_id"));
            productDtoResponse.setProductName(row.get("product_name").toString());
            productDtoResponse.setSerialNumber(row.get("serial_number").toString());
            productDtoResponse.setProductPrice((Integer) row.get("product_price"));
            productDtoResponse.setProductImage(row.get("product_image").toString());
            productDtoResponse.setProductSold((Integer) row.get("product_sold"));
            productDtoResponse.setStok((Integer) row.get("stok"));
            productDtoResponse.setProductSoldOver((Integer) row.get("product_sold_over"));
            productDtoResponse.setCreatedAt((Timestamp) row.get("created_date"));

            resultList.add(productDtoResponse);
        }

        String sqlQty = "select count(1) from pos_master.m_product where warung_pos_identity = ?";

        int totalItem = dataBaseConnection.queryForObject(sqlQty, new Object[]{warungPosIdentity}, Integer.class);

        int totalPage = PaginationUtils.getPagination(totalItem,parSize);

        ListResultResponse<ProductDtoResponse> resultResponse = new ListResultResponse<>();
        Metadata metadata = Metadata.builder()
                .page(parPage)
                .total_items(parSize)
                .total_pages(totalPage)
                .total_items(totalItem)
                .build();

        resultResponse.setMetadata(metadata);
        resultResponse.setData(resultList);


        return resultResponse;
    }

    @Override
    public void createProductDB(ProductDtoRequest request, String warungPosIdentity) {
        String sql = "insert into pos_master.m_product(serial_number, product_name, product_price, product_image, category_level_1, category_level_2, warung_pos_identity) " +
                "values(?,?,?,?,?,?,?)";
//        String sql = "call pos_master.product_create(?,?,?,?,?)";

        Object[] params = {
                request.getSerial_number(),
                request.getProduct_name(),
                request.getProduct_price(),
                request.getProduct_image(),
                request.getProduct_category_1(),
                request.getProduct_category_2(),
                warungPosIdentity
        };

        dataBaseConnection.update(sql, params);
    }

    @Override
    public List<ProductDtoResponse> findAllProductByOwnerDB(String warungPosIdentity) {

        String sql = "select * from pos_master.m_product where warung_pos_identity = ?";


        List<Map<String, Object>> resultData = dataBaseConnection.queryForList(sql, warungPosIdentity);

        List<ProductDtoResponse> resultList = new ArrayList<>();

        for (Map<String, Object> row : resultData) {

            ProductDtoResponse productDtoResponse = new ProductDtoResponse();

            productDtoResponse.setId((Integer) row.get("product_id"));
            productDtoResponse.setProductName(row.get("product_name").toString());
            productDtoResponse.setSerialNumber(row.get("serial_number").toString());
            productDtoResponse.setProductPrice((Integer) row.get("product_price"));
            productDtoResponse.setProductImage(row.get("product_image").toString());
            productDtoResponse.setCreatedAt((Date) row.get("created_at"));

            resultList.add(productDtoResponse);
        }

        return resultList;
    }

//    @Override
//    public void checkoutProductDB(List<ProductDtoCheckoutRequest> request, String warungPosIdentity) {
//        request.stream().forEach(val-> {
//            String sql = "select * from pos_master.m_product where id = ?";
//
//            ProductDtoResponse resultData = (ProductDtoResponse) dataBaseConnection.queryForObject(sql, new Object[]{val.getId()}, new RowMapper<ProductDtoResponse>() {
//                @Override
//                public ProductDtoResponse mapRow(ResultSet row, int rowNumber) throws SQLException {
//                    ProductDtoResponse productDtoResponse = new ProductDtoResponse();
//
//                    productDtoResponse.setId(row.getInt("id"));
//                    productDtoResponse.setProductName(row.getString("product_name"));
//                    productDtoResponse.setSerialNumber(row.getString("serial_number"));
//                    productDtoResponse.setProductPrice(row.getInt("product_price"));
//                    productDtoResponse.setProductImage(row.getString("product_image"));
//                    productDtoResponse.setProductSold((row.getInt("product_sold")));
//                    productDtoResponse.setStok(row.getInt("stok"));
//                    productDtoResponse.setProductSoldOver(row.getInt("product_sold_over"));
//
//                    return productDtoResponse;
//                };
//            });
//
//            int totalStokDefisit = resultData.getStok() - val.getQtyItem();
//
//            if(resultData.getStok() != 0 && totalStokDefisit >= 0){
//                resultData.setStok(resultData.getStok() - val.getQtyItem());
//                resultData.setProductSold(resultData.getProductSold() + val.getQtyItem());
//            }else{
//                resultData.setStok(0);
//                resultData.setProductSoldOver(resultData.getProductSoldOver() + (val.getQtyItem() - resultData.getStok()));
//                resultData.setProductSold(resultData.getProductSold() + val.getQtyItem());
//            }
//
//            String sqlUpdate = "UPDATE pos_master.m_product SET product_sold = ?, stok = ?, product_sold_over = ? WHERE id = ?";
//
//            Object[] params = {
//                    resultData.getProductSold(),
//                    resultData.getStok(),
//                    resultData.getProductSoldOver(),
//                    val.getId()
//            };
//
//            dataBaseConnection.update(sqlUpdate, params);
//        });
//    }

    @Override
    public void checkoutProductDB(CartDtoCheckoutRequest request, String warungPosIdentity, Integer user_id) {
        String sql = "call pos_master.product_checkout(?, ?, ?, ?, ?, ?, ?, ?)";
        ObjectMapper objectMapper = new ObjectMapper();

        try (Connection connection = dataSource.getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {

            // Convert list of ProductDtoCheckoutRequest to JSONB array
            Array jsonArray = connection.createArrayOf("jsonb", request.getList_item().stream()
                    .map(dto -> {
                        try {
                            return objectMapper.writeValueAsString(dto);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace(); // or log the exception properly
                            return null;
                        }
                    })
                    .toArray(String[]::new));

            log.info("isi data : {}", jsonArray);

            // Set parameters
            statement.setArray(1, jsonArray);
            statement.setString(2, warungPosIdentity);
            statement.setInt(3, user_id);
            statement.setString(4, request.getInvoice());
            statement.setInt(5, request.getTotal_price());
            statement.setInt(6, request.getPajak());
            statement.setInt(7, request.getDiscount());
            statement.setInt(8, request.getQtyCart());
            // Execute the procedure
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // or log the exception properly
        }
    }

    @Override
    public List<ProductDtoResponse> findProductByCategoryLevel1DB(String id, String warungPosIdentity) {
        String sql = "select * from pos_master.m_product where warung_pos_identity = ? and category_level_1 = ?";

        int idUnik = Integer.parseInt(id);

        Object[] params = {
                warungPosIdentity,
                idUnik,
        };

        List<Map<String, Object>> resultData = dataBaseConnection.queryForList(sql, params);

        List<ProductDtoResponse> resultList = new ArrayList<>();

        for (Map<String, Object> row : resultData) {

            ProductDtoResponse productDtoResponse = new ProductDtoResponse();

            productDtoResponse.setId((Integer) row.get("product_id"));
            productDtoResponse.setProductName(row.get("product_name").toString());
            productDtoResponse.setSerialNumber(row.get("serial_number").toString());
            productDtoResponse.setProductPrice((Integer) row.get("product_price"));
            productDtoResponse.setProductImage(row.get("product_image").toString());
            productDtoResponse.setProductSold((Integer) row.get("product_sold"));
            productDtoResponse.setStok((Integer) row.get("stok"));
            productDtoResponse.setProductSoldOver((Integer) row.get("product_sold_over"));
            productDtoResponse.setCreatedAt((Date) row.get("created_at"));

            resultList.add(productDtoResponse);
        }

        return resultList;
    }

    @Override
    public List<ProductDtoResponse> findProductByCategoryLevel2DB(String id, String warungPosIdentity) {
        String sql = "select * from pos_master.m_product where warung_pos_identity = ? and category_level_2 = ?";


        int idUnik = Integer.parseInt(id);

        Object[] params = {
                warungPosIdentity,
                idUnik,
        };

        List<Map<String, Object>> resultData = dataBaseConnection.queryForList(sql, params);

        List<ProductDtoResponse> resultList = new ArrayList<>();

        for (Map<String, Object> row : resultData) {

            ProductDtoResponse productDtoResponse = new ProductDtoResponse();

            productDtoResponse.setId((Integer) row.get("product_id"));
            productDtoResponse.setProductName(row.get("product_name").toString());
            productDtoResponse.setSerialNumber(row.get("serial_number").toString());
            productDtoResponse.setProductPrice((Integer) row.get("product_price"));
            productDtoResponse.setProductImage(row.get("product_image").toString());
            productDtoResponse.setProductSold((Integer) row.get("product_sold"));
            productDtoResponse.setStok((Integer) row.get("stok"));
            productDtoResponse.setProductSoldOver((Integer) row.get("product_sold_over"));
            productDtoResponse.setCreatedAt((Date) row.get("created_at"));

            resultList.add(productDtoResponse);
        }

        return resultList;
    }

    @Override
    public ListResultResponse<ProductDtoResponse> findAllProductNameByOwnerDB(String product, String warungPosIdentity, String page, String size) {
        int parPage = Integer.parseInt(page);
        int parSize = Integer.parseInt(size);

        int offset = ( parPage - 1 ) * parSize;

        String sql = "SELECT * FROM pos_master.m_product mp where lower(mp.product_name) LIKE CONCAT('%', lower(?), '%') and mp.warung_pos_identity = ? order by mp.product_id offset ? limit ?";

        Object[] params = {
                product,
                warungPosIdentity,
                offset,
                parSize
        };

        List<Map<String, Object>> resultData = dataBaseConnection.queryForList(sql, params);

        List<ProductDtoResponse> resultList = new ArrayList<>();

        for (Map<String, Object> row : resultData) {

            ProductDtoResponse productDtoResponse = new ProductDtoResponse();

            productDtoResponse.setId((Integer) row.get("id"));
            productDtoResponse.setProductName(row.get("product_name").toString());
            productDtoResponse.setSerialNumber(row.get("serial_number").toString());
            productDtoResponse.setProductPrice((Integer) row.get("product_price"));
            productDtoResponse.setProductImage(row.get("product_image").toString());
            productDtoResponse.setProductSold((Integer) row.get("product_sold"));
            productDtoResponse.setStok((Integer) row.get("stok"));
            productDtoResponse.setProductSoldOver((Integer) row.get("product_sold_over"));
            productDtoResponse.setCreatedAt((Timestamp) row.get("created_date"));

            resultList.add(productDtoResponse);
        }

        String sqlQty = "select count(1) from pos_master.m_product where lower(product_name) LIKE CONCAT('%', lower(?), '%') and warung_pos_identity = ?";

        int totalItem = dataBaseConnection.queryForObject(sqlQty, new Object[]{product, warungPosIdentity}, Integer.class);

        int totalPage = PaginationUtils.getPagination(totalItem,parSize);

        ListResultResponse<ProductDtoResponse> resultResponse = new ListResultResponse<>();
        Metadata metadata = Metadata.builder()
                .page(parPage)
                .total_items(parSize)
                .total_pages(totalPage)
                .total_items(totalItem)
                .build();

        resultResponse.setMetadata(metadata);
        resultResponse.setData(resultList);


        return resultResponse;
    }

    @Override
    public ListResultResponse<ProductDtoResponse> findProductByCoordinateDB(String warungPosIdentity, String page, String size) {

        int parPage = Integer.parseInt(page);
        int parSize = Integer.parseInt(size);

        int offset = ( parPage - 1 ) * parSize;

        String sql = "select * from pos_master.v_product_warung";

        List<Map<String, Object>> resultData = dataBaseConnection.queryForList(sql);

        List<ProductDtoResponse> resultList = new ArrayList<>();

        for (Map<String, Object> row : resultData) {

            ProductDtoResponse productDtoResponse = new ProductDtoResponse();

            productDtoResponse.setId((Integer) row.get("id"));
            productDtoResponse.setProductName(row.get("product_name").toString());
            productDtoResponse.setSerialNumber(row.get("serial_number").toString());
            productDtoResponse.setProductPrice((Integer) row.get("product_price"));
            productDtoResponse.setProductImage(row.get("product_image").toString());
            productDtoResponse.setProductSold((Integer) row.get("product_sold"));
            productDtoResponse.setStok((Integer) row.get("stok"));
            productDtoResponse.setProductSoldOver((Integer) row.get("product_sold_over"));
            productDtoResponse.setCreatedAt((Timestamp) row.get("created_date"));
            productDtoResponse.setLat((Double) row.get("lat"));
            productDtoResponse.setLng((Double) row.get("lng"));

            resultList.add(productDtoResponse);
        }

        String sqlQty = "select count(1) from pos_master.v_product_warung";

        int totalItem = dataBaseConnection.queryForObject(sqlQty, Integer.class);

        int totalPage = PaginationUtils.getPagination(totalItem,parSize);

        ListResultResponse<ProductDtoResponse> resultResponse = new ListResultResponse<>();

        Metadata metadata = Metadata.builder()
                .page(parPage)
                .total_items(parSize)
                .total_pages(totalPage)
                .total_items(totalItem)
                .build();

        resultResponse.setMetadata(metadata);

        resultResponse.setData(resultList);

        return resultResponse;
    }
}
