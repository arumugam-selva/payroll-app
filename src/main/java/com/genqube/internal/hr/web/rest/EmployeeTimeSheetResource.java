package com.genqube.internal.hr.web.rest;

import com.genqube.internal.hr.domain.EmployeeTimeSheet;
import com.genqube.internal.hr.service.EmployeeTimeSheetService;
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
 * REST controller for managing {@link com.genqube.internal.hr.domain.EmployeeTimeSheet}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeTimeSheetResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeTimeSheetResource.class);

    private static final String ENTITY_NAME = "employeeTimeSheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeTimeSheetService employeeTimeSheetService;

    public EmployeeTimeSheetResource(EmployeeTimeSheetService employeeTimeSheetService) {
        this.employeeTimeSheetService = employeeTimeSheetService;
    }

    /**
     * {@code POST  /employee-time-sheets} : Create a new employeeTimeSheet.
     *
     * @param employeeTimeSheet the employeeTimeSheet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeTimeSheet, or with status {@code 400 (Bad Request)} if the employeeTimeSheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-time-sheets")
    public ResponseEntity<EmployeeTimeSheet> createEmployeeTimeSheet(@RequestBody EmployeeTimeSheet employeeTimeSheet) throws URISyntaxException {
        log.debug("REST request to save EmployeeTimeSheet : {}", employeeTimeSheet);
        if (employeeTimeSheet.getId() != null) {
            throw new BadRequestAlertException("A new employeeTimeSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeTimeSheet result = employeeTimeSheetService.save(employeeTimeSheet);
        return ResponseEntity.created(new URI("/api/employee-time-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-time-sheets} : Updates an existing employeeTimeSheet.
     *
     * @param employeeTimeSheet the employeeTimeSheet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeTimeSheet,
     * or with status {@code 400 (Bad Request)} if the employeeTimeSheet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeTimeSheet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-time-sheets")
    public ResponseEntity<EmployeeTimeSheet> updateEmployeeTimeSheet(@RequestBody EmployeeTimeSheet employeeTimeSheet) throws URISyntaxException {
        log.debug("REST request to update EmployeeTimeSheet : {}", employeeTimeSheet);
        if (employeeTimeSheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeTimeSheet result = employeeTimeSheetService.save(employeeTimeSheet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeTimeSheet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-time-sheets} : get all the employeeTimeSheets.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeTimeSheets in body.
     */
    @GetMapping("/employee-time-sheets")
    public ResponseEntity<List<EmployeeTimeSheet>> getAllEmployeeTimeSheets(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeTimeSheets");
        Page<EmployeeTimeSheet> page = employeeTimeSheetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-time-sheets/:id} : get the "id" employeeTimeSheet.
     *
     * @param id the id of the employeeTimeSheet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeTimeSheet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-time-sheets/{id}")
    public ResponseEntity<EmployeeTimeSheet> getEmployeeTimeSheet(@PathVariable Long id) {
        log.debug("REST request to get EmployeeTimeSheet : {}", id);
        Optional<EmployeeTimeSheet> employeeTimeSheet = employeeTimeSheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeTimeSheet);
    }

    /**
     * {@code DELETE  /employee-time-sheets/:id} : delete the "id" employeeTimeSheet.
     *
     * @param id the id of the employeeTimeSheet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-time-sheets/{id}")
    public ResponseEntity<Void> deleteEmployeeTimeSheet(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeTimeSheet : {}", id);
        employeeTimeSheetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
