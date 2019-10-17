package com.genqube.internal.hr.service;

import com.genqube.internal.hr.domain.EmployeeDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EmployeeDetails}.
 */
public interface EmployeeDetailsService {

    /**
     * Save a employeeDetails.
     *
     * @param employeeDetails the entity to save.
     * @return the persisted entity.
     */
    EmployeeDetails save(EmployeeDetails employeeDetails);

    /**
     * Get all the employeeDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeDetails> findAll(Pageable pageable);


    /**
     * Get the "id" employeeDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeDetails> findOne(Long id);

    /**
     * Delete the "id" employeeDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
