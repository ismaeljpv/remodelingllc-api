package com.remodelingllc.api.resource;

import com.remodelingllc.api.entity.DbTablesInfo;
import com.remodelingllc.api.service.DbTablesInfoService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DbTablesInfoResource {

    private final DbTablesInfoService dbTablesInfoService;

    public DbTablesInfoResource(final DbTablesInfoService dbTablesInfoService) {
        this.dbTablesInfoService = dbTablesInfoService;
    }

    @GetMapping(value = "/tables/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public List<DbTablesInfo> findAll() {
        return dbTablesInfoService.findAll();
    }
}

