package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.DbTablesInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbTablesInfoRepository extends CrudRepository<DbTablesInfo, Long> {

    List<DbTablesInfo> findAllByOrderByTotalSizeDesc();

}
