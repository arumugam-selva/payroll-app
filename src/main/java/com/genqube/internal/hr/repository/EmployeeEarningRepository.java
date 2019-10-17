package com.genqube.internal.hr.repository;
import com.genqube.internal.hr.domain.EmployeeEarning;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeEarning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeEarningRepository extends JpaRepository<EmployeeEarning, Long> {

}
