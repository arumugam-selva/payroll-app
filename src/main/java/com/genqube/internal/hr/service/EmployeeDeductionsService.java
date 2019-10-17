package com.genqube.internal.hr.service;

import com.genqube.internal.hr.domain.EmployeeDeductions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EmployeeDeductions}.
 */
public interface EmployeeDeductionsService {

    /**
     * Save a employeeDeductions.
     *
     * @param employeeDeductions the entity to save.
     * @return the persisted entity.
     */
    EmployeeDeductions save(EmployeeDeductions employeeDeductions);

    /**
     * Get all the employeeDeductions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeDeductions> findAll(Pageable pageable);


    /**
     * Get the "id" employeeDeductions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeDeductions> findOne(Long id);

    /**
     * Delete the "id" employeeDeductions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
