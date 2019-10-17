package com.genqube.internal.hr.web.rest;

import com.genqube.internal.hr.domain.EmployeeDeductions;
import com.genqube.internal.hr.service.EmployeeDeductionsService;
import com.genqube.internal.hr.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.genqube.internal.hr.domain.EmployeeDeductions}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeDeductionsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeDeductionsResource.class);

    private static final String ENTITY_NAME = "employeeDeductions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeDeductionsService employeeDeductionsService;

    public EmployeeDeductionsResource(EmployeeDeductionsService employeeDeductionsService) {
        this.employeeDeductionsService = employeeDeductionsService;
    }

    /**
     * {@code POST  /employee-deductions} : Create a new employeeDeductions.
     *
     * @param employeeDeductions the employeeDeductions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDeductions, or with status {@code 400 (Bad Request)} if the employeeDeductions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-deductions")
    public ResponseEntity<EmployeeDeductions> createEmployeeDeductions(@RequestBody EmployeeDeductions employeeDeductions) throws URISyntaxException {
        log.debug("REST request to save EmployeeDeductions : {}", employeeDeductions);
        if (employeeDeductions.getId() != null) {
            throw new BadRequestAlertException("A new employeeDeductions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeDeductions result = employeeDeductionsService.save(employeeDeductions);
        return ResponseEntity.created(new URI("/api/employee-deductions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-deductions} : Updates an existing employeeDeductions.
     *
     * @param employeeDeductions the employeeDeductions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDeductions,
     * or with status {@code 400 (Bad Request)} if the employeeDeductions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDeductions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-deductions")
    public ResponseEntity<EmployeeDeductions> updateEmployeeDeductions(@RequestBody EmployeeDeductions employeeDeductions) throws URISyntaxException {
        log.debug("REST request to update EmployeeDeductions : {}", employeeDeductions);
        if (employeeDeductions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeDeductions result = employeeDeductionsService.save(employeeDeductions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeDeductions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-deductions} : get all the employeeDeductions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeDeductions in body.
     */
    @GetMapping("/employee-deductions")
    public ResponseEntity<List<EmployeeDeductions>> getAllEmployeeDeductions(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeDeductions");
        Page<EmployeeDeductions> page = employeeDeductionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-deductions/:id} : get the "id" employeeDeductions.
     *
     * @param id the id of the employeeDeductions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDeductions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-deductions/{id}")
    public ResponseEntity<EmployeeDeductions> getEmployeeDeductions(@PathVariable Long id) {
        log.debug("REST request to get EmployeeDeductions : {}", id);
        Optional<EmployeeDeductions> employeeDeductions = employeeDeductionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDeductions);
    }

    /**
     * {@code DELETE  /employee-deductions/:id} : delete the "id" employeeDeductions.
     *
     * @param id the id of the employeeDeductions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-deductions/{id}")
    public ResponseEntity<Void> deleteEmployeeDeductions(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeDeductions : {}", id);
        employeeDeductionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
