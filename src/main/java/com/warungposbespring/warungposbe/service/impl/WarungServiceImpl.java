package com.warungposbespring.warungposbe.service.impl;

import com.warungposbespring.warungposbe.dao.WarungDao;
import com.warungposbespring.warungposbe.dto.ListResultResponse;
import com.warungposbespring.warungposbe.dto.WarungResponseDto;
import com.warungposbespring.warungposbe.service.WarungService;
import org.springframework.stereotype.Service;

@Service
public class WarungServiceImpl implements WarungService {

    private final WarungDao warungDao;

    public WarungServiceImpl(WarungDao warungDao) {
        this.warungDao = warungDao;
    }

    @Override
    public ListResultResponse<WarungResponseDto> findAllWarung(String page, String size, String sort) {
        return warungDao.findAllWarungDB(page, size, sort);
    }
}
