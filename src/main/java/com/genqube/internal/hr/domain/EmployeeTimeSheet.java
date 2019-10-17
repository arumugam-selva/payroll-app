package com.genqube.internal.hr.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EmployeeTimeSheet.
 */
@Entity
@Table(name = "employee_time_sheet")
public class EmployeeTimeSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "no_of_working_days")
    private Long noOfWorkingDays;

    @Column(name = "no_of_leavs")
    private Long noOfLeavs;

    @Column(name = "no_of_lop")
    private Long noOfLop;

    @Column(name = "no_of_arear_days")
    private Long noOfArearDays;

    @ManyToOne
    @JsonIgnoreProperties("employeeTimeSheets")
    private Employee employeeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public EmployeeTimeSheet employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getMonth() {
        return month;
    }

    public EmployeeTimeSheet month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public EmployeeTimeSheet year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getNoOfWorkingDays() {
        return noOfWorkingDays;
    }

    public EmployeeTimeSheet noOfWorkingDays(Long noOfWorkingDays) {
        this.noOfWorkingDays = noOfWorkingDays;
        return this;
    }

    public void setNoOfWorkingDays(Long noOfWorkingDays) {
        this.noOfWorkingDays = noOfWorkingDays;
    }

    public Long getNoOfLeavs() {
        return noOfLeavs;
    }

    public EmployeeTimeSheet noOfLeavs(Long noOfLeavs) {
        this.noOfLeavs = noOfLeavs;
        return this;
    }

    public void setNoOfLeavs(Long noOfLeavs) {
        this.noOfLeavs = noOfLeavs;
    }

    public Long getNoOfLop() {
        return noOfLop;
    }

    public EmployeeTimeSheet noOfLop(Long noOfLop) {
        this.noOfLop = noOfLop;
        return this;
    }

    public void setNoOfLop(Long noOfLop) {
        this.noOfLop = noOfLop;
    }

    public Long getNoOfArearDays() {
        return noOfArearDays;
    }

    public EmployeeTimeSheet noOfArearDays(Long noOfArearDays) {
        this.noOfArearDays = noOfArearDays;
        return this;
    }

    public void setNoOfArearDays(Long noOfArearDays) {
        this.noOfArearDays = noOfArearDays;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public EmployeeTimeSheet employeeId(Employee employee) {
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
        if (!(o instanceof EmployeeTimeSheet)) {
            return false;
        }
        return id != null && id.equals(((EmployeeTimeSheet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeeTimeSheet{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", month=" + getMonth() +
            ", year=" + getYear() +
            ", noOfWorkingDays=" + getNoOfWorkingDays() +
            ", noOfLeavs=" + getNoOfLeavs() +
            ", noOfLop=" + getNoOfLop() +
            ", noOfArearDays=" + getNoOfArearDays() +
            "}";
    }
}
