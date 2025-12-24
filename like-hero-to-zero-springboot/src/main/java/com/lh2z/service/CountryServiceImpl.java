package com.lh2z.service;

import com.lh2z.domain.Country;
import com.lh2z.repo.CountryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepo countryRepo;

    public CountryServiceImpl(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> listAllOrdered() {
        return countryRepo.findAllByOrderByNameAsc();
    }

    @Override
    @Transactional
    public Country create(String id, String name, String region) {
        Country c = new Country();
        c.setId(id.trim().toUpperCase());   // ISO3
        c.setName(name.trim());
        c.setRegion(region == null ? null : region.trim());
        return countryRepo.save(c);
    }
}
