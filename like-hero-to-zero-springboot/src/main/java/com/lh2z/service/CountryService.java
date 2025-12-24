package com.lh2z.service;

import com.lh2z.domain.Country;
import java.util.List;

public interface CountryService {

    List<Country> listAllOrdered();
    Country create(String id, String name, String region);
}
