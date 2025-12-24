package com.lh2z.web;

import com.lh2z.domain.Country;
import com.lh2z.domain.EmissionRecord;
import com.lh2z.repo.CountryRepo;
import com.lh2z.service.EmissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;

@Controller
public class PublicController {

    private final EmissionService emissionService;
    private final CountryRepo countryRepo;

    public PublicController(EmissionService emissionService, CountryRepo countryRepo) {
        this.emissionService = emissionService;
        this.countryRepo = countryRepo;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(value = "iso3", required = false) String iso3,
            Model model,
            HttpServletRequest request
    ) {
        // Land Browsersprache ermitteln    	
        String iso2 = Optional.ofNullable(request.getLocale())
                .map(Locale::getCountry)
                .orElse("");
        String visitorIso3 = null;
        if (iso2 != null && !iso2.isBlank()) {
            try {
                visitorIso3 = new Locale("", iso2).getISO3Country();
            } catch (MissingResourceException ignored) { }
        }
        if (visitorIso3 == null || visitorIso3.isBlank()) {
            visitorIso3 = "DEU"; // Fallback, falls nicht ermittelbar
        }

        // Besucherland-Name + Summe CO2 kt        
        String visitorCountryName = countryRepo.findById(visitorIso3)
                .map(Country::getName)
                .orElse(visitorIso3);
        BigDecimal visitorTotalKt = emissionService.totalApprovedByCountry(visitorIso3);
        if (visitorTotalKt == null) visitorTotalKt = BigDecimal.ZERO;

        model.addAttribute("visitorIso3", visitorIso3);
        model.addAttribute("visitorCountryName", visitorCountryName);
        model.addAttribute("visitorTotalKt", visitorTotalKt);

        // Filter über iso3        
        String selectedIso3 = (iso3 != null && !iso3.isBlank()) ? iso3 : null;
        model.addAttribute("selectedIso3", selectedIso3);

        if (selectedIso3 != null) {
            List<EmissionRecord> byCountry = emissionService.listApprovedForCountry(selectedIso3);
            model.addAttribute("records", byCountry);
            BigDecimal totalKt = emissionService.totalApprovedByCountry(selectedIso3);
            model.addAttribute("totalKt", totalKt == null ? BigDecimal.ZERO : totalKt);
        } else {
            List<EmissionRecord> all = emissionService.listApprovedOrdered();
            model.addAttribute("records", all);
        }

        // Länder-Liste für Filter-Dropdowns        
        model.addAttribute("countries", countryRepo.findAll());

        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
