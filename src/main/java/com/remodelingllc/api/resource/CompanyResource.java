package com.remodelingllc.api.resource;

import com.remodelingllc.api.entity.Company;
import com.remodelingllc.api.service.CompanyService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyResource {

    private final CompanyService companyService;

    public CompanyResource(final CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company findById(@PathVariable final int id) {
        return companyService.findById(id);
    }

    @PostMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Company save(@Validated @RequestBody final Company company) {
        return companyService.save(company);
    }

    @PutMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Company update (@Validated @RequestBody final Company company) {
        return companyService.update(company);
    }
}
