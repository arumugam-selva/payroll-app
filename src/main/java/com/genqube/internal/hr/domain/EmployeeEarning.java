package com.genqube.internal.hr.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EmployeeEarning.
 */
@Entity
@Table(name = "employee_earning")
public class EmployeeEarning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "effective_date")
    private Integer effectiveDate;

    @Column(name = "basic")
    private Long basic;

    @Column(name = "hra")
    private Long hra;

    @Column(name = "conveyance")
    private Long conveyance;

    @Column(name = "leave_encash")
    private Long leaveEncash;

    @Column(name = "special_allowance")
    private Long specialAllowance;

    @Column(name = "reimbursement")
    private Long reimbursement;

    @ManyToOne
    @JsonIgnoreProperties("employeeEarnings")
    private Employee employeeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEffectiveDate() {
        return effectiveDate;
    }

    public EmployeeEarning effectiveDate(Integer effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(Integer effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Long getBasic() {
        return basic;
    }

    public EmployeeEarning basic(Long basic) {
        this.basic = basic;
        return this;
    }

    public void setBasic(Long basic) {
        this.basic = basic;
    }

    public Long getHra() {
        return hra;
    }

    public EmployeeEarning hra(Long hra) {
        this.hra = hra;
        return this;
    }

    public void setHra(Long hra) {
        this.hra = hra;
    }

    public Long getConveyance() {
        return conveyance;
    }

    public EmployeeEarning conveyance(Long conveyance) {
        this.conveyance = conveyance;
        return this;
    }

    public void setConveyance(Long conveyance) {
        this.conveyance = conveyance;
    }

    public Long getLeaveEncash() {
        return leaveEncash;
    }

    public EmployeeEarning leaveEncash(Long leaveEncash) {
        this.leaveEncash = leaveEncash;
        return this;
    }

    public void setLeaveEncash(Long leaveEncash) {
        this.leaveEncash = leaveEncash;
    }

    public Long getSpecialAllowance() {
        return specialAllowance;
    }

    public EmployeeEarning specialAllowance(Long specialAllowance) {
        this.specialAllowance = specialAllowance;
        return this;
    }

    public void setSpecialAllowance(Long specialAllowance) {
        this.specialAllowance = specialAllowance;
    }

    public Long getReimbursement() {
        return reimbursement;
    }

    public EmployeeEarning reimbursement(Long reimbursement) {
        this.reimbursement = reimbursement;
        return this;
    }

    public void setReimbursement(Long reimbursement) {
        this.reimbursement = reimbursement;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public EmployeeEarning employeeId(Employee employee) {
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
        if (!(o instanceof EmployeeEarning)) {
            return false;
        }
        return id != null && id.equals(((EmployeeEarning) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeeEarning{" +
            "id=" + getId() +
            ", effectiveDate=" + getEffectiveDate() +
            ", basic=" + getBasic() +
            ", hra=" + getHra() +
            ", conveyance=" + getConveyance() +
            ", leaveEncash=" + getLeaveEncash() +
            ", specialAllowance=" + getSpecialAllowance() +
            ", reimbursement=" + getReimbursement() +
            "}";
    }
}
