package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.Company;
import com.remodelingllc.api.exception.EntityNotFoundException;
import com.remodelingllc.api.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(final CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company findById(final int id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new EntityNotFoundException("Company Not Found");
        }
        return company.get();
    }

    public Company save(final Company company) {
        return companyRepository.save(company);
    }

    public Company update(final Company company) {
        Optional<Company> oldCompany = companyRepository.findById(company.getId());
        if (oldCompany.isEmpty()) {
            throw new EntityNotFoundException("Company Not Found");
        }
        return companyRepository.save(company);
    }
}
