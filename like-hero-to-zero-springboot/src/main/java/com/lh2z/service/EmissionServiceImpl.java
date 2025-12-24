package com.lh2z.service;

import com.lh2z.domain.Country;
import com.lh2z.domain.EmissionRecord;
import com.lh2z.domain.EmissionRecord.Status;
import com.lh2z.repo.CountryRepo;
import com.lh2z.repo.EmissionRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmissionServiceImpl implements EmissionService {

    private final EmissionRepo emissionRepo;
    private final CountryRepo countryRepo;

    public EmissionServiceImpl(EmissionRepo emissionRepo, CountryRepo countryRepo) {
        this.emissionRepo = emissionRepo;
        this.countryRepo = countryRepo;
    }

    // PublicController
    @Override
    public List<EmissionRecord> listApprovedForCountry(String iso3) {
        return emissionRepo.findApprovedByCountry(iso3.toUpperCase());
    }

    @Override
    public List<EmissionRecord> listApprovedOrdered() {
        return emissionRepo.findAllApprovedOrdered();
    }

    @Override
    public BigDecimal totalApprovedByCountry(String iso3) {
        BigDecimal sum = emissionRepo.sumApprovedByCountry(iso3.toUpperCase());
        return sum != null ? sum : BigDecimal.ZERO;
    }

    // AdminController
    @Override
    public Page<EmissionRecord> findPending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return emissionRepo.findPending(pageable);
    }

    @Override
    public void approve(Long id) {
        EmissionRecord r = emissionRepo.findById(id).orElseThrow();
        r.setStatus(Status.APPROVED);
        emissionRepo.save(r);
    }

    @Override
    public void reject(Long id) {
        EmissionRecord r = emissionRepo.findById(id).orElseThrow();
        r.setStatus(Status.REJECTED);
        emissionRepo.save(r);
    }

    @Override
    public EmissionRecord create(String iso3, short year, BigDecimal co2kt, String source, String company) {
        Country c = countryRepo.findById(iso3.toUpperCase()).orElseThrow();
        EmissionRecord r = new EmissionRecord();
        r.setCountry(c);
        r.setYear(year);
        r.setValueKt(co2kt);
        r.setSource(source);
        r.setCompany(company);
        r.setStatus(Status.PENDING);
        return emissionRepo.save(r);
    }

    @Override
    public EmissionRecord update(Long id, BigDecimal co2kt, String source, String company) {
        EmissionRecord r = emissionRepo.findById(id).orElseThrow();
        r.setValueKt(co2kt);
        r.setSource(source);
        r.setCompany(company);
        return emissionRepo.save(r);
    }
}
