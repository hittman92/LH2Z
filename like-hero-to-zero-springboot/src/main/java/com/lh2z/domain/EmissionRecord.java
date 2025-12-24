package com.lh2z.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "emission_record")
public class EmissionRecord {

    public enum Status { PENDING, APPROVED, REJECTED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "year_", nullable = false)
    private short year;

    @Column(name = "value_kt", nullable = false, precision = 38, scale = 2)
    private BigDecimal valueKt;

    @Column(nullable = false)
    private String source;

    @Column(nullable = true)
    private String company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public short getYear() { return year; }
    public void setYear(short year) { this.year = year; }

    public BigDecimal getValueKt() { return valueKt; }
    public void setValueKt(BigDecimal valueKt) { this.valueKt = valueKt; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
