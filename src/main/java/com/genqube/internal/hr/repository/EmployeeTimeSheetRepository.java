package com.genqube.internal.hr.repository;
import com.genqube.internal.hr.domain.EmployeeTimeSheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeTimeSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeTimeSheetRepository extends JpaRepository<EmployeeTimeSheet, Long> {

}
