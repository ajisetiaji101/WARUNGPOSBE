package com.warungposbespring.warungposbe.dao.impl;

import com.warungposbespring.warungposbe.dao.OrderDao;
import com.warungposbespring.warungposbe.dto.OrderResponseDto;
import com.warungposbespring.warungposbe.dto.ProductDtoResponse;
import com.warungposbespring.warungposbe.dto.UserForJwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
@Slf4j
public class OrderDaoImpl implements OrderDao {

    @Autowired
    @Qualifier("DBTemplate")
    private JdbcTemplate dataBaseConnection;

    @Override
    public int getOrderNextDB(UserForJwtResponse user) {

        String sql = "select order_total from pos_master.m_orders mo where mo.warung_id = ? order by created_at desc limit 1";

        int orderTotal = dataBaseConnection.queryForObject(sql, new Object[]{user.getWarung_pos_identity()}, Integer.class);

        return orderTotal;
    }

    @Override
    public List<OrderResponseDto> getOrderByOwnerDB(UserForJwtResponse user) {

        List<OrderResponseDto> data = new ArrayList<>();

        String sql = "select * from pos_master.m_orders mo where mo.warung_id = ? order by mo.created_at asc";
        String sqlFindUsername = "select mu.name from pos_master.m_users mu where mu.user_id = ?";

        List<Map<String, Object>> resultData = dataBaseConnection.queryForList(sql, user.getWarung_pos_identity());

        for (Map<String, Object> e : resultData) {

            String sqlOrderDetail = "select * from pos_master.m_order_details od where od.order_id = ?";
            List<ProductDtoResponse> dataProduct = new ArrayList<>();
            List<Map<String, Object>> resultDataDetail = dataBaseConnection.queryForList(sqlOrderDetail, e.get("order_id"));

            for (Map<String, Object> ep : resultDataDetail) {

                String sqlProduct = "select * from pos_master.m_product mp where mp.product_id = ?";

                ProductDtoResponse dataProducts = (ProductDtoResponse) dataBaseConnection.queryForObject(sqlProduct, new Object[]{ep.get("product_id")}, new RowMapper<ProductDtoResponse>() {
                    @Override
                    public ProductDtoResponse mapRow(ResultSet row, int rowNum) throws SQLException {

                        ProductDtoResponse productDtoResponse = new ProductDtoResponse();

                        productDtoResponse.setId(row.getInt("product_id"));
                        productDtoResponse.setProductName(row.getString("product_name"));
                        productDtoResponse.setSerialNumber(row.getString("serial_number"));
                        productDtoResponse.setProductPrice((Integer) ep.get("price"));
                        productDtoResponse.setProductImage(row.getString("product_image"));
                        productDtoResponse.setProductSold(row.getInt("product_sold"));
                        productDtoResponse.setStok(row.getInt("stok"));
                        productDtoResponse.setProductSoldOver(row.getInt("product_sold_over"));
                        productDtoResponse.setQty((Integer) ep.get("qty"));

                        return productDtoResponse;
                    }
                });
                dataProduct.add(dataProducts);
            }

            String username = dataBaseConnection.queryForObject(sqlFindUsername, new Object[]{(Integer) e.get("user_id")}, String.class);


            OrderResponseDto dataOrder = OrderResponseDto.builder()
                    .order_id((Integer) e.get("order_id"))
                    .order_price((Integer) e.get("order_price"))
                    .order_total((Integer) e.get("order_total"))
                    .qty((Integer) e.get("qty"))
                    .invoice((String) e.get("invoice"))
                    .pajak((Integer) e.get("pajak"))
                    .discount((Integer) e.get("discount"))
                    .user_id((Integer) e.get("user_id"))
                    .user_name(username)
                    .warung_id((String) e.get("warung_id"))
                    .list_product(dataProduct)
                    .build();

            data.add(dataOrder);
        }

        return data;
    }
}
