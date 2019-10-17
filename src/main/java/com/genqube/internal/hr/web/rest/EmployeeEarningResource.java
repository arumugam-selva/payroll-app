package com.genqube.internal.hr.web.rest;

import com.genqube.internal.hr.domain.EmployeeEarning;
import com.genqube.internal.hr.service.EmployeeEarningService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.genqube.internal.hr.domain.EmployeeEarning}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeEarningResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeEarningResource.class);

    private static final String ENTITY_NAME = "employeeEarning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeEarningService employeeEarningService;

    public EmployeeEarningResource(EmployeeEarningService employeeEarningService) {
        this.employeeEarningService = employeeEarningService;
    }

    /**
     * {@code POST  /employee-earnings} : Create a new employeeEarning.
     *
     * @param employeeEarning the employeeEarning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeEarning, or with status {@code 400 (Bad Request)} if the employeeEarning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-earnings")
    public ResponseEntity<EmployeeEarning> createEmployeeEarning(@Valid @RequestBody EmployeeEarning employeeEarning) throws URISyntaxException {
        log.debug("REST request to save EmployeeEarning : {}", employeeEarning);
        if (employeeEarning.getId() != null) {
            throw new BadRequestAlertException("A new employeeEarning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeEarning result = employeeEarningService.save(employeeEarning);
        return ResponseEntity.created(new URI("/api/employee-earnings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-earnings} : Updates an existing employeeEarning.
     *
     * @param employeeEarning the employeeEarning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeEarning,
     * or with status {@code 400 (Bad Request)} if the employeeEarning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeEarning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-earnings")
    public ResponseEntity<EmployeeEarning> updateEmployeeEarning(@Valid @RequestBody EmployeeEarning employeeEarning) throws URISyntaxException {
        log.debug("REST request to update EmployeeEarning : {}", employeeEarning);
        if (employeeEarning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeEarning result = employeeEarningService.save(employeeEarning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeEarning.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-earnings} : get all the employeeEarnings.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeEarnings in body.
     */
    @GetMapping("/employee-earnings")
    public ResponseEntity<List<EmployeeEarning>> getAllEmployeeEarnings(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeEarnings");
        Page<EmployeeEarning> page = employeeEarningService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-earnings/:id} : get the "id" employeeEarning.
     *
     * @param id the id of the employeeEarning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeEarning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-earnings/{id}")
    public ResponseEntity<EmployeeEarning> getEmployeeEarning(@PathVariable Long id) {
        log.debug("REST request to get EmployeeEarning : {}", id);
        Optional<EmployeeEarning> employeeEarning = employeeEarningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeEarning);
    }

    /**
     * {@code DELETE  /employee-earnings/:id} : delete the "id" employeeEarning.
     *
     * @param id the id of the employeeEarning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-earnings/{id}")
    public ResponseEntity<Void> deleteEmployeeEarning(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeEarning : {}", id);
        employeeEarningService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
