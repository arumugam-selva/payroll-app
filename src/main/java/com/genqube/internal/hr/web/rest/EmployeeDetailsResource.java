package com.genqube.internal.hr.web.rest;

import com.genqube.internal.hr.domain.EmployeeDetails;
import com.genqube.internal.hr.service.EmployeeDetailsService;
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
 * REST controller for managing {@link com.genqube.internal.hr.domain.EmployeeDetails}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeDetailsResource.class);

    private static final String ENTITY_NAME = "employeeDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeDetailsService employeeDetailsService;

    public EmployeeDetailsResource(EmployeeDetailsService employeeDetailsService) {
        this.employeeDetailsService = employeeDetailsService;
    }

    /**
     * {@code POST  /employee-details} : Create a new employeeDetails.
     *
     * @param employeeDetails the employeeDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDetails, or with status {@code 400 (Bad Request)} if the employeeDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-details")
    public ResponseEntity<EmployeeDetails> createEmployeeDetails(@RequestBody EmployeeDetails employeeDetails) throws URISyntaxException {
        log.debug("REST request to save EmployeeDetails : {}", employeeDetails);
        if (employeeDetails.getId() != null) {
            throw new BadRequestAlertException("A new employeeDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeDetails result = employeeDetailsService.save(employeeDetails);
        return ResponseEntity.created(new URI("/api/employee-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-details} : Updates an existing employeeDetails.
     *
     * @param employeeDetails the employeeDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDetails,
     * or with status {@code 400 (Bad Request)} if the employeeDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-details")
    public ResponseEntity<EmployeeDetails> updateEmployeeDetails(@RequestBody EmployeeDetails employeeDetails) throws URISyntaxException {
        log.debug("REST request to update EmployeeDetails : {}", employeeDetails);
        if (employeeDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeDetails result = employeeDetailsService.save(employeeDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-details} : get all the employeeDetails.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeDetails in body.
     */
    @GetMapping("/employee-details")
    public ResponseEntity<List<EmployeeDetails>> getAllEmployeeDetails(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeDetails");
        Page<EmployeeDetails> page = employeeDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-details/:id} : get the "id" employeeDetails.
     *
     * @param id the id of the employeeDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-details/{id}")
    public ResponseEntity<EmployeeDetails> getEmployeeDetails(@PathVariable Long id) {
        log.debug("REST request to get EmployeeDetails : {}", id);
        Optional<EmployeeDetails> employeeDetails = employeeDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDetails);
    }

    /**
     * {@code DELETE  /employee-details/:id} : delete the "id" employeeDetails.
     *
     * @param id the id of the employeeDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-details/{id}")
    public ResponseEntity<Void> deleteEmployeeDetails(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeDetails : {}", id);
        employeeDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
