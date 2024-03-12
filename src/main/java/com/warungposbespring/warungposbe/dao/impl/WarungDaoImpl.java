package com.warungposbespring.warungposbe.dao.impl;

import com.warungposbespring.warungposbe.dao.WarungDao;
import com.warungposbespring.warungposbe.dto.ListResultResponse;
import com.warungposbespring.warungposbe.dto.Metadata;
import com.warungposbespring.warungposbe.dto.UserResponse;
import com.warungposbespring.warungposbe.dto.WarungResponseDto;
import com.warungposbespring.warungposbe.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class WarungDaoImpl implements WarungDao {

    @Autowired
    @Qualifier("DBTemplate")
    private JdbcTemplate dataBaseConnection;


    @Override
    public ListResultResponse<WarungResponseDto> findAllWarungDB(String page, String size, String sort) {

        int parPage = Integer.parseInt(page);
        int parSize = Integer.parseInt(size);

        int offset = ( parPage - 1 ) * parSize;

        String sqlGetAllWarung = "select * from pos_master.m_warung";
        String sqlCount = "select count(*) from pos_master.m_warung";

        List<Map<String, Object>> result = dataBaseConnection.queryForList(sqlGetAllWarung);

        List<WarungResponseDto> data = new ArrayList<WarungResponseDto>();

        for (Map<String, Object> map : result) {
            WarungResponseDto warung = WarungResponseDto.builder()
                    .id_warung((String) map.get("id_warung"))
                    .nama_warung((String) map.get("nama_warung"))
                    .alamat((String) map.get("alamat"))
                    .telephone((String) map.get("telephone"))
                    .path_logo((String) map.get("path_logo"))
                    .lat((Double) map.get("lat"))
                    .lng((Double) map.get("lng"))
                    .verified((Boolean) map.get("verified"))
                    .email_user((String) map.get("email_user"))
                    .build();
            data.add(warung);
        }

        int count = dataBaseConnection.queryForObject(sqlCount, Integer.class);

        int totalPage = PaginationUtils.getPagination(count,parSize);

        Metadata metadata = Metadata.builder()
                .page(parPage)
                .total_items(parSize)
                .total_pages(totalPage)
                .total_items(count)
                .build();

        ListResultResponse<WarungResponseDto> resultResponse = new ListResultResponse<>();

        resultResponse.setData(data);
        resultResponse.setMetadata(metadata);

        return resultResponse;

    };
}
