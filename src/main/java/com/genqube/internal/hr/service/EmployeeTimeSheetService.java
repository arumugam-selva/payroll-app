package com.genqube.internal.hr.service;

import com.genqube.internal.hr.domain.EmployeeTimeSheet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EmployeeTimeSheet}.
 */
public interface EmployeeTimeSheetService {

    /**
     * Save a employeeTimeSheet.
     *
     * @param employeeTimeSheet the entity to save.
     * @return the persisted entity.
     */
    EmployeeTimeSheet save(EmployeeTimeSheet employeeTimeSheet);

    /**
     * Get all the employeeTimeSheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeTimeSheet> findAll(Pageable pageable);


    /**
     * Get the "id" employeeTimeSheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeTimeSheet> findOne(Long id);

    /**
     * Delete the "id" employeeTimeSheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
