package com.genqube.internal.hr.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.genqube.internal.hr.domain.enumeration.Organization;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "org")
    private Organization org;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organization getOrg() {
        return org;
    }

    public Employee org(Organization org) {
        this.org = org;
        return this;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public String getStatus() {
        return status;
    }

    public Employee status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", org='" + getOrg() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
