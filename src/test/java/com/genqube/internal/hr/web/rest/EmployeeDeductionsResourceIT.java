package com.genqube.internal.hr.web.rest;

import com.genqube.internal.hr.PayRollApplicationApp;
import com.genqube.internal.hr.domain.EmployeeDeductions;
import com.genqube.internal.hr.repository.EmployeeDeductionsRepository;
import com.genqube.internal.hr.service.EmployeeDeductionsService;
import com.genqube.internal.hr.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.genqube.internal.hr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeDeductionsResource} REST controller.
 */
@SpringBootTest(classes = PayRollApplicationApp.class)
public class EmployeeDeductionsResourceIT {

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_PF = 1L;
    private static final Long UPDATED_PF = 2L;

    private static final Long DEFAULT_PROF_TAX = 1L;
    private static final Long UPDATED_PROF_TAX = 2L;

    private static final Long DEFAULT_INCOME_TAX = 1L;
    private static final Long UPDATED_INCOME_TAX = 2L;

    private static final Long DEFAULT_LOP = 1L;
    private static final Long UPDATED_LOP = 2L;

    @Autowired
    private EmployeeDeductionsRepository employeeDeductionsRepository;

    @Autowired
    private EmployeeDeductionsService employeeDeductionsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEmployeeDeductionsMockMvc;

    private EmployeeDeductions employeeDeductions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeDeductionsResource employeeDeductionsResource = new EmployeeDeductionsResource(employeeDeductionsService);
        this.restEmployeeDeductionsMockMvc = MockMvcBuilders.standaloneSetup(employeeDeductionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDeductions createEntity(EntityManager em) {
        EmployeeDeductions employeeDeductions = new EmployeeDeductions()
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .pf(DEFAULT_PF)
            .profTax(DEFAULT_PROF_TAX)
            .incomeTax(DEFAULT_INCOME_TAX)
            .lop(DEFAULT_LOP);
        return employeeDeductions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDeductions createUpdatedEntity(EntityManager em) {
        EmployeeDeductions employeeDeductions = new EmployeeDeductions()
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .pf(UPDATED_PF)
            .profTax(UPDATED_PROF_TAX)
            .incomeTax(UPDATED_INCOME_TAX)
            .lop(UPDATED_LOP);
        return employeeDeductions;
    }

    @BeforeEach
    public void initTest() {
        employeeDeductions = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeDeductions() throws Exception {
        int databaseSizeBeforeCreate = employeeDeductionsRepository.findAll().size();

        // Create the EmployeeDeductions
        restEmployeeDeductionsMockMvc.perform(post("/api/employee-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDeductions)))
            .andExpect(status().isCreated());

        // Validate the EmployeeDeductions in the database
        List<EmployeeDeductions> employeeDeductionsList = employeeDeductionsRepository.findAll();
        assertThat(employeeDeductionsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDeductions testEmployeeDeductions = employeeDeductionsList.get(employeeDeductionsList.size() - 1);
        assertThat(testEmployeeDeductions.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testEmployeeDeductions.getPf()).isEqualTo(DEFAULT_PF);
        assertThat(testEmployeeDeductions.getProfTax()).isEqualTo(DEFAULT_PROF_TAX);
        assertThat(testEmployeeDeductions.getIncomeTax()).isEqualTo(DEFAULT_INCOME_TAX);
        assertThat(testEmployeeDeductions.getLop()).isEqualTo(DEFAULT_LOP);
    }

    @Test
    @Transactional
    public void createEmployeeDeductionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeDeductionsRepository.findAll().size();

        // Create the EmployeeDeductions with an existing ID
        employeeDeductions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDeductionsMockMvc.perform(post("/api/employee-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDeductions)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDeductions in the database
        List<EmployeeDeductions> employeeDeductionsList = employeeDeductionsRepository.findAll();
        assertThat(employeeDeductionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEffectiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeDeductionsRepository.findAll().size();
        // set the field null
        employeeDeductions.setEffectiveDate(null);

        // Create the EmployeeDeductions, which fails.

        restEmployeeDeductionsMockMvc.perform(post("/api/employee-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDeductions)))
            .andExpect(status().isBadRequest());

        List<EmployeeDeductions> employeeDeductionsList = employeeDeductionsRepository.findAll();
        assertThat(employeeDeductionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeDeductions() throws Exception {
        // Initialize the database
        employeeDeductionsRepository.saveAndFlush(employeeDeductions);

        // Get all the employeeDeductionsList
        restEmployeeDeductionsMockMvc.perform(get("/api/employee-deductions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDeductions.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].pf").value(hasItem(DEFAULT_PF.intValue())))
            .andExpect(jsonPath("$.[*].profTax").value(hasItem(DEFAULT_PROF_TAX.intValue())))
            .andExpect(jsonPath("$.[*].incomeTax").value(hasItem(DEFAULT_INCOME_TAX.intValue())))
            .andExpect(jsonPath("$.[*].lop").value(hasItem(DEFAULT_LOP.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeDeductions() throws Exception {
        // Initialize the database
        employeeDeductionsRepository.saveAndFlush(employeeDeductions);

        // Get the employeeDeductions
        restEmployeeDeductionsMockMvc.perform(get("/api/employee-deductions/{id}", employeeDeductions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDeductions.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.pf").value(DEFAULT_PF.intValue()))
            .andExpect(jsonPath("$.profTax").value(DEFAULT_PROF_TAX.intValue()))
            .andExpect(jsonPath("$.incomeTax").value(DEFAULT_INCOME_TAX.intValue()))
            .andExpect(jsonPath("$.lop").value(DEFAULT_LOP.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeDeductions() throws Exception {
        // Get the employeeDeductions
        restEmployeeDeductionsMockMvc.perform(get("/api/employee-deductions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeDeductions() throws Exception {
        // Initialize the database
        employeeDeductionsService.save(employeeDeductions);

        int databaseSizeBeforeUpdate = employeeDeductionsRepository.findAll().size();

        // Update the employeeDeductions
        EmployeeDeductions updatedEmployeeDeductions = employeeDeductionsRepository.findById(employeeDeductions.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDeductions are not directly saved in db
        em.detach(updatedEmployeeDeductions);
        updatedEmployeeDeductions
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .pf(UPDATED_PF)
            .profTax(UPDATED_PROF_TAX)
            .incomeTax(UPDATED_INCOME_TAX)
            .lop(UPDATED_LOP);

        restEmployeeDeductionsMockMvc.perform(put("/api/employee-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeDeductions)))
            .andExpect(status().isOk());

        // Validate the EmployeeDeductions in the database
        List<EmployeeDeductions> employeeDeductionsList = employeeDeductionsRepository.findAll();
        assertThat(employeeDeductionsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDeductions testEmployeeDeductions = employeeDeductionsList.get(employeeDeductionsList.size() - 1);
        assertThat(testEmployeeDeductions.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testEmployeeDeductions.getPf()).isEqualTo(UPDATED_PF);
        assertThat(testEmployeeDeductions.getProfTax()).isEqualTo(UPDATED_PROF_TAX);
        assertThat(testEmployeeDeductions.getIncomeTax()).isEqualTo(UPDATED_INCOME_TAX);
        assertThat(testEmployeeDeductions.getLop()).isEqualTo(UPDATED_LOP);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeDeductions() throws Exception {
        int databaseSizeBeforeUpdate = employeeDeductionsRepository.findAll().size();

        // Create the EmployeeDeductions

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDeductionsMockMvc.perform(put("/api/employee-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDeductions)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDeductions in the database
        List<EmployeeDeductions> employeeDeductionsList = employeeDeductionsRepository.findAll();
        assertThat(employeeDeductionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeDeductions() throws Exception {
        // Initialize the database
        employeeDeductionsService.save(employeeDeductions);

        int databaseSizeBeforeDelete = employeeDeductionsRepository.findAll().size();

        // Delete the employeeDeductions
        restEmployeeDeductionsMockMvc.perform(delete("/api/employee-deductions/{id}", employeeDeductions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDeductions> employeeDeductionsList = employeeDeductionsRepository.findAll();
        assertThat(employeeDeductionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDeductions.class);
        EmployeeDeductions employeeDeductions1 = new EmployeeDeductions();
        employeeDeductions1.setId(1L);
        EmployeeDeductions employeeDeductions2 = new EmployeeDeductions();
        employeeDeductions2.setId(employeeDeductions1.getId());
        assertThat(employeeDeductions1).isEqualTo(employeeDeductions2);
        employeeDeductions2.setId(2L);
        assertThat(employeeDeductions1).isNotEqualTo(employeeDeductions2);
        employeeDeductions1.setId(null);
        assertThat(employeeDeductions1).isNotEqualTo(employeeDeductions2);
    }
}
