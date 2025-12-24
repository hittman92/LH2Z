package com.lh2z.web;

import com.lh2z.domain.Country;
import com.lh2z.domain.EmissionRecord;
import com.lh2z.service.CountryService;
import com.lh2z.service.EmissionService;
import com.lh2z.web.dto.EmissionRecordForm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CountryService countryService;
    private final EmissionService emissionService;

    public AdminController(EmissionService emissionService, CountryService countryService) {
        this.emissionService = emissionService;
        this.countryService = countryService;
    }

    @ModelAttribute("countries")
    public List<Country> countries() {
        return countryService.listAllOrdered();
    }

    // Länder
    @GetMapping("/countries")
    public String countriesPage(Model model) {
        return "admin_countries";
    }

    @PostMapping("/countries")
    public String createCountry(@RequestParam String iso3,
                                @RequestParam String name,
                                @RequestParam(required = false) String region) {
        countryService.create(iso3, name, region);
        return "redirect:/admin/countries";
    }

    // Emissionen 
    @GetMapping("/emissions")
    public String emissionsPage(@RequestParam(name = "iso3", required = false) String iso3,
                                @RequestParam(name = "company", required = false) String company,
                                @RequestParam(name = "status", required = false) String status,
                                Model model) {
        model.addAttribute("filterIso3", iso3);
        model.addAttribute("filterCompany", company);
        model.addAttribute("filterStatus", status);
        model.addAttribute("form", new EmissionRecordForm());

        List<EmissionRecord> records = emissionService.listApprovedOrdered();
        model.addAttribute("records", records);

        return "admin_emissions";
    }

    // Neuen Eintrag anlegen    
    @PostMapping("/emissions")
    public String createEmission(@ModelAttribute("form") EmissionRecordForm form,
                                 RedirectAttributes ra) {
        emissionService.create(
                form.getCountryIso3(),
                form.getYear().shortValue(),
                form.getValueKt(),
                form.getSource(),
                form.getCompany()
        );
        ra.addFlashAttribute("msg", "Eintrag gespeichert (PENDING).");
        return "redirect:/admin/emissions";
    }

    // Existierenden Eintrag aktualisieren    
    @PostMapping("/emissions/{id}")
    public String updateEmission(@PathVariable Long id,
                                 @RequestParam BigDecimal co2kt,
                                 @RequestParam String source,
                                 @RequestParam String company) {
        emissionService.update(id, co2kt, source, company);
        return "redirect:/admin/emissions";
    }

    // Review-Liste
    @GetMapping("/review")
    public String review(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "25") int size,
                         Model model) {
        Page<EmissionRecord> p = emissionService.findPending(page, size);
        model.addAttribute("page", p);
        return "admin_review";
    }

    @PostMapping("/emissions/{id}/approve")
    public String approve(@PathVariable Long id,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "25") int size) {
        emissionService.approve(id);
        return "redirect:/admin/review?page=" + page + "&size=" + size;
    }

    @PostMapping("/emissions/{id}/reject")
    public String reject(@PathVariable Long id,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "25") int size) {
        emissionService.reject(id);
        return "redirect:/admin/review?page=" + page + "&size=" + size;
    }
}
