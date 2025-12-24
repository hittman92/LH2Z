package com.lh2z.repo;

import com.lh2z.domain.EmissionRecord;
import com.lh2z.domain.EmissionRecord.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EmissionRepo extends JpaRepository<EmissionRecord, Long>, JpaSpecificationExecutor<EmissionRecord> {

    // Für PublicController

    // Alle freigegebenen Einträge eines Landes
    @Query("""
           SELECT e
           FROM EmissionRecord e
           WHERE e.country.id = :iso3
             AND e.status = com.lh2z.domain.EmissionRecord$Status.APPROVED
           ORDER BY e.year DESC
           """)
    List<EmissionRecord> findApprovedByCountry(@Param("iso3") String iso3);

    // Alle freigegebenen Einträge global
    @Query("""
           SELECT e
           FROM EmissionRecord e
           WHERE e.status = com.lh2z.domain.EmissionRecord$Status.APPROVED
           ORDER BY e.year DESC
           """)
    List<EmissionRecord> findAllApprovedOrdered();

    // Summe aller freigegebenen Werte (kt) eines Landes
    @Query("""
           SELECT COALESCE(SUM(e.valueKt), 0)
           FROM EmissionRecord e
           WHERE e.country.id = :iso3
             AND e.status = com.lh2z.domain.EmissionRecord$Status.APPROVED
           """)
    BigDecimal sumApprovedByCountry(@Param("iso3") String iso3);


    // Für AdminController

    // PENDING-Liste aufsteigend
    @Query("""
           SELECT e
           FROM EmissionRecord e
           WHERE e.status = com.lh2z.domain.EmissionRecord$Status.PENDING
           ORDER BY e.createdAt ASC
           """)
    Page<EmissionRecord> findPending(Pageable pageable);

    // APPROVED-Liste absteigend
    @Query("""
           SELECT e
           FROM EmissionRecord e
           WHERE e.status = com.lh2z.domain.EmissionRecord$Status.APPROVED
           ORDER BY e.year DESC
           """)
    Page<EmissionRecord> findApproved(Pageable pageable);

    // Allgemeine Suche/Filter
    @Query("""
           SELECT e
           FROM EmissionRecord e
           WHERE (:iso3 IS NULL OR e.country.id = :iso3)
             AND (:company IS NULL OR LOWER(e.company) LIKE LOWER(CONCAT('%', :company, '%')))
             AND (:status IS NULL OR e.status = :status)
           ORDER BY e.year DESC
           """)
    Page<EmissionRecord> search(
            @Param("iso3") String iso3,
            @Param("company") String company,
            @Param("status") Status status,
            Pageable pageable
    );
}
