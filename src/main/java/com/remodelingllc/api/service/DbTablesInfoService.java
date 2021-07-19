package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.DbTablesInfo;
import com.remodelingllc.api.repository.DbTablesInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbTablesInfoService {

    private DbTablesInfoRepository dbTablesInfoRepository;

    public DbTablesInfoService(final DbTablesInfoRepository dbTablesInfoRepository) {
        this.dbTablesInfoRepository = dbTablesInfoRepository;
    }

    public List<DbTablesInfo> findAll() {
        return dbTablesInfoRepository.findAllByOrderByTotalSizeDesc();
    }
}

