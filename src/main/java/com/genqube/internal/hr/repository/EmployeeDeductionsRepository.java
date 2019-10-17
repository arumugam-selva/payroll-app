package com.genqube.internal.hr.repository;
import com.genqube.internal.hr.domain.EmployeeDeductions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeDeductions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeDeductionsRepository extends JpaRepository<EmployeeDeductions, Long> {

}
