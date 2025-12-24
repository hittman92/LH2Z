package com.lh2z.web.dto;

import java.math.BigDecimal;

public class EmissionRecordForm {
    private String countryIso3;
    private Integer year;
    private BigDecimal valueKt;
    private String source;
    private String company;

    public String getCountryIso3() { return countryIso3; }
    public void setCountryIso3(String countryIso3) { this.countryIso3 = countryIso3; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public BigDecimal getValueKt() { return valueKt; }
    public void setValueKt(BigDecimal valueKt) { this.valueKt = valueKt; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
}
