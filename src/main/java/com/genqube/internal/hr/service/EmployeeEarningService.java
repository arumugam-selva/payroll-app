package com.genqube.internal.hr.service;

import com.genqube.internal.hr.domain.EmployeeEarning;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EmployeeEarning}.
 */
public interface EmployeeEarningService {

    /**
     * Save a employeeEarning.
     *
     * @param employeeEarning the entity to save.
     * @return the persisted entity.
     */
    EmployeeEarning save(EmployeeEarning employeeEarning);

    /**
     * Get all the employeeEarnings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeEarning> findAll(Pageable pageable);


    /**
     * Get the "id" employeeEarning.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeEarning> findOne(Long id);

    /**
     * Delete the "id" employeeEarning.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
