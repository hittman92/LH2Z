package com.lh2z.service;

import com.lh2z.domain.EmissionRecord;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface EmissionService {

    // PublicController
    List<EmissionRecord> listApprovedForCountry(String iso3);
    List<EmissionRecord> listApprovedOrdered();
    BigDecimal totalApprovedByCountry(String iso3);

    // AdminController
    Page<EmissionRecord> findPending(int page, int size);
    void approve(Long id);
    void reject(Long id);

    EmissionRecord create(String iso3, short year, BigDecimal co2kt, String source, String company);
    EmissionRecord update(Long id, BigDecimal co2kt, String source, String company);
}
