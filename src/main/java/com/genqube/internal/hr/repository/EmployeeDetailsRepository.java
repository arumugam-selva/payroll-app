package com.genqube.internal.hr.repository;
import com.genqube.internal.hr.domain.EmployeeDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

}
