package com.genqube.internal.hr.service.impl;

import com.genqube.internal.hr.service.EmployeeDeductionsService;
import com.genqube.internal.hr.domain.EmployeeDeductions;
import com.genqube.internal.hr.repository.EmployeeDeductionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeDeductions}.
 */
@Service
@Transactional
public class EmployeeDeductionsServiceImpl implements EmployeeDeductionsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeDeductionsServiceImpl.class);

    private final EmployeeDeductionsRepository employeeDeductionsRepository;

    public EmployeeDeductionsServiceImpl(EmployeeDeductionsRepository employeeDeductionsRepository) {
        this.employeeDeductionsRepository = employeeDeductionsRepository;
    }

    /**
     * Save a employeeDeductions.
     *
     * @param employeeDeductions the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmployeeDeductions save(EmployeeDeductions employeeDeductions) {
        log.debug("Request to save EmployeeDeductions : {}", employeeDeductions);
        return employeeDeductionsRepository.save(employeeDeductions);
    }

    /**
     * Get all the employeeDeductions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDeductions> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeDeductions");
        return employeeDeductionsRepository.findAll(pageable);
    }


    /**
     * Get one employeeDeductions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDeductions> findOne(Long id) {
        log.debug("Request to get EmployeeDeductions : {}", id);
        return employeeDeductionsRepository.findById(id);
    }

    /**
     * Delete the employeeDeductions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeDeductions : {}", id);
        employeeDeductionsRepository.deleteById(id);
    }
}
