package com.lh2z.repo;

import com.lh2z.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CountryRepo extends JpaRepository<Country, String> {

    List<Country> findAllByOrderByNameAsc();
}



