package com.genqube.internal.hr.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmployeeDeductions.
 */
@Entity
@Table(name = "employee_deductions")
public class EmployeeDeductions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "pf")
    private Long pf;

    @Column(name = "prof_tax")
    private Long profTax;

    @Column(name = "income_tax")
    private Long incomeTax;

    @Column(name = "lop")
    private Long lop;

    @ManyToOne
    @JsonIgnoreProperties("employeeDeductions")
    private Employee employeeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public EmployeeDeductions effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Long getPf() {
        return pf;
    }

    public EmployeeDeductions pf(Long pf) {
        this.pf = pf;
        return this;
    }

    public void setPf(Long pf) {
        this.pf = pf;
    }

    public Long getProfTax() {
        return profTax;
    }

    public EmployeeDeductions profTax(Long profTax) {
        this.profTax = profTax;
        return this;
    }

    public void setProfTax(Long profTax) {
        this.profTax = profTax;
    }

    public Long getIncomeTax() {
        return incomeTax;
    }

    public EmployeeDeductions incomeTax(Long incomeTax) {
        this.incomeTax = incomeTax;
        return this;
    }

    public void setIncomeTax(Long incomeTax) {
        this.incomeTax = incomeTax;
    }

    public Long getLop() {
        return lop;
    }

    public EmployeeDeductions lop(Long lop) {
        this.lop = lop;
        return this;
    }

    public void setLop(Long lop) {
        this.lop = lop;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public EmployeeDeductions employeeId(Employee employee) {
        this.employeeId = employee;
        return this;
    }

    public void setEmployeeId(Employee employee) {
        this.employeeId = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDeductions)) {
            return false;
        }
        return id != null && id.equals(((EmployeeDeductions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeeDeductions{" +
            "id=" + getId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", pf=" + getPf() +
            ", profTax=" + getProfTax() +
            ", incomeTax=" + getIncomeTax() +
            ", lop=" + getLop() +
            "}";
    }
}
