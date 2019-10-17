package com.genqube.internal.hr.service.impl;

import com.genqube.internal.hr.service.EmployeeDetailsService;
import com.genqube.internal.hr.domain.EmployeeDetails;
import com.genqube.internal.hr.repository.EmployeeDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeDetails}.
 */
@Service
@Transactional
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeDetailsServiceImpl.class);

    private final EmployeeDetailsRepository employeeDetailsRepository;

    public EmployeeDetailsServiceImpl(EmployeeDetailsRepository employeeDetailsRepository) {
        this.employeeDetailsRepository = employeeDetailsRepository;
    }

    /**
     * Save a employeeDetails.
     *
     * @param employeeDetails the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmployeeDetails save(EmployeeDetails employeeDetails) {
        log.debug("Request to save EmployeeDetails : {}", employeeDetails);
        return employeeDetailsRepository.save(employeeDetails);
    }

    /**
     * Get all the employeeDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDetails> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeDetails");
        return employeeDetailsRepository.findAll(pageable);
    }


    /**
     * Get one employeeDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDetails> findOne(Long id) {
        log.debug("Request to get EmployeeDetails : {}", id);
        return employeeDetailsRepository.findById(id);
    }

    /**
     * Delete the employeeDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeDetails : {}", id);
        employeeDetailsRepository.deleteById(id);
    }
}
