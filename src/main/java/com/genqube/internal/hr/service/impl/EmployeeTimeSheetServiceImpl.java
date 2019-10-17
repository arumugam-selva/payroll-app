package com.genqube.internal.hr.service.impl;

import com.genqube.internal.hr.service.EmployeeTimeSheetService;
import com.genqube.internal.hr.domain.EmployeeTimeSheet;
import com.genqube.internal.hr.repository.EmployeeTimeSheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeTimeSheet}.
 */
@Service
@Transactional
public class EmployeeTimeSheetServiceImpl implements EmployeeTimeSheetService {

    private final Logger log = LoggerFactory.getLogger(EmployeeTimeSheetServiceImpl.class);

    private final EmployeeTimeSheetRepository employeeTimeSheetRepository;

    public EmployeeTimeSheetServiceImpl(EmployeeTimeSheetRepository employeeTimeSheetRepository) {
        this.employeeTimeSheetRepository = employeeTimeSheetRepository;
    }

    /**
     * Save a employeeTimeSheet.
     *
     * @param employeeTimeSheet the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmployeeTimeSheet save(EmployeeTimeSheet employeeTimeSheet) {
        log.debug("Request to save EmployeeTimeSheet : {}", employeeTimeSheet);
        return employeeTimeSheetRepository.save(employeeTimeSheet);
    }

    /**
     * Get all the employeeTimeSheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeTimeSheet> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeTimeSheets");
        return employeeTimeSheetRepository.findAll(pageable);
    }


    /**
     * Get one employeeTimeSheet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeTimeSheet> findOne(Long id) {
        log.debug("Request to get EmployeeTimeSheet : {}", id);
        return employeeTimeSheetRepository.findById(id);
    }

    /**
     * Delete the employeeTimeSheet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeTimeSheet : {}", id);
        employeeTimeSheetRepository.deleteById(id);
    }
}
