package com.genqube.internal.hr.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmployeeDetails.
 */
@Entity
@Table(name = "employee_details")
public class EmployeeDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private Integer department;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "gender")
    private String gender;

    @Column(name = "bank")
    private String bank;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JsonIgnoreProperties("employeeDetails")
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

    public EmployeeDetails employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public EmployeeDetails email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public EmployeeDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public EmployeeDetails designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getDepartment() {
        return department;
    }

    public EmployeeDetails department(Integer department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public LocalDate getDob() {
        return dob;
    }

    public EmployeeDetails dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public EmployeeDetails joiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
        return this;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPanNo() {
        return panNo;
    }

    public EmployeeDetails panNo(String panNo) {
        this.panNo = panNo;
        return this;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public EmployeeDetails bankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
        return this;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getGender() {
        return gender;
    }

    public EmployeeDetails gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBank() {
        return bank;
    }

    public EmployeeDetails bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getLocation() {
        return location;
    }

    public EmployeeDetails location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public EmployeeDetails employeeId(Employee employee) {
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
        if (!(o instanceof EmployeeDetails)) {
            return false;
        }
        return id != null && id.equals(((EmployeeDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeeDetails{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", department=" + getDepartment() +
            ", dob='" + getDob() + "'" +
            ", joiningDate='" + getJoiningDate() + "'" +
            ", panNo='" + getPanNo() + "'" +
            ", bankAccountNo='" + getBankAccountNo() + "'" +
            ", gender='" + getGender() + "'" +
            ", bank='" + getBank() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
