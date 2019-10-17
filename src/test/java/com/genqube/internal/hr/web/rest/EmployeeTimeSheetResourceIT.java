package com.genqube.internal.hr.web.rest;

import com.genqube.internal.hr.PayRollApplicationApp;
import com.genqube.internal.hr.domain.EmployeeTimeSheet;
import com.genqube.internal.hr.repository.EmployeeTimeSheetRepository;
import com.genqube.internal.hr.service.EmployeeTimeSheetService;
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
import java.util.List;

import static com.genqube.internal.hr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeTimeSheetResource} REST controller.
 */
@SpringBootTest(classes = PayRollApplicationApp.class)
public class EmployeeTimeSheetResourceIT {

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_YEAR = 2016;
    private static final Integer UPDATED_YEAR = 2017;

    private static final Long DEFAULT_NO_OF_WORKING_DAYS = 1L;
    private static final Long UPDATED_NO_OF_WORKING_DAYS = 2L;

    private static final Long DEFAULT_NO_OF_LEAVS = 1L;
    private static final Long UPDATED_NO_OF_LEAVS = 2L;

    private static final Long DEFAULT_NO_OF_LOP = 1L;
    private static final Long UPDATED_NO_OF_LOP = 2L;

    private static final Long DEFAULT_NO_OF_AREAR_DAYS = 1L;
    private static final Long UPDATED_NO_OF_AREAR_DAYS = 2L;

    @Autowired
    private EmployeeTimeSheetRepository employeeTimeSheetRepository;

    @Autowired
    private EmployeeTimeSheetService employeeTimeSheetService;

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

    private MockMvc restEmployeeTimeSheetMockMvc;

    private EmployeeTimeSheet employeeTimeSheet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeTimeSheetResource employeeTimeSheetResource = new EmployeeTimeSheetResource(employeeTimeSheetService);
        this.restEmployeeTimeSheetMockMvc = MockMvcBuilders.standaloneSetup(employeeTimeSheetResource)
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
    public static EmployeeTimeSheet createEntity(EntityManager em) {
        EmployeeTimeSheet employeeTimeSheet = new EmployeeTimeSheet()
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR)
            .noOfWorkingDays(DEFAULT_NO_OF_WORKING_DAYS)
            .noOfLeavs(DEFAULT_NO_OF_LEAVS)
            .noOfLop(DEFAULT_NO_OF_LOP)
            .noOfArearDays(DEFAULT_NO_OF_AREAR_DAYS);
        return employeeTimeSheet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeTimeSheet createUpdatedEntity(EntityManager em) {
        EmployeeTimeSheet employeeTimeSheet = new EmployeeTimeSheet()
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .noOfWorkingDays(UPDATED_NO_OF_WORKING_DAYS)
            .noOfLeavs(UPDATED_NO_OF_LEAVS)
            .noOfLop(UPDATED_NO_OF_LOP)
            .noOfArearDays(UPDATED_NO_OF_AREAR_DAYS);
        return employeeTimeSheet;
    }

    @BeforeEach
    public void initTest() {
        employeeTimeSheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeTimeSheet() throws Exception {
        int databaseSizeBeforeCreate = employeeTimeSheetRepository.findAll().size();

        // Create the EmployeeTimeSheet
        restEmployeeTimeSheetMockMvc.perform(post("/api/employee-time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeTimeSheet)))
            .andExpect(status().isCreated());

        // Validate the EmployeeTimeSheet in the database
        List<EmployeeTimeSheet> employeeTimeSheetList = employeeTimeSheetRepository.findAll();
        assertThat(employeeTimeSheetList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeTimeSheet testEmployeeTimeSheet = employeeTimeSheetList.get(employeeTimeSheetList.size() - 1);
        assertThat(testEmployeeTimeSheet.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testEmployeeTimeSheet.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testEmployeeTimeSheet.getNoOfWorkingDays()).isEqualTo(DEFAULT_NO_OF_WORKING_DAYS);
        assertThat(testEmployeeTimeSheet.getNoOfLeavs()).isEqualTo(DEFAULT_NO_OF_LEAVS);
        assertThat(testEmployeeTimeSheet.getNoOfLop()).isEqualTo(DEFAULT_NO_OF_LOP);
        assertThat(testEmployeeTimeSheet.getNoOfArearDays()).isEqualTo(DEFAULT_NO_OF_AREAR_DAYS);
    }

    @Test
    @Transactional
    public void createEmployeeTimeSheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeTimeSheetRepository.findAll().size();

        // Create the EmployeeTimeSheet with an existing ID
        employeeTimeSheet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeTimeSheetMockMvc.perform(post("/api/employee-time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeTimeSheet)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTimeSheet in the database
        List<EmployeeTimeSheet> employeeTimeSheetList = employeeTimeSheetRepository.findAll();
        assertThat(employeeTimeSheetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeTimeSheetRepository.findAll().size();
        // set the field null
        employeeTimeSheet.setMonth(null);

        // Create the EmployeeTimeSheet, which fails.

        restEmployeeTimeSheetMockMvc.perform(post("/api/employee-time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeTimeSheet)))
            .andExpect(status().isBadRequest());

        List<EmployeeTimeSheet> employeeTimeSheetList = employeeTimeSheetRepository.findAll();
        assertThat(employeeTimeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeTimeSheetRepository.findAll().size();
        // set the field null
        employeeTimeSheet.setYear(null);

        // Create the EmployeeTimeSheet, which fails.

        restEmployeeTimeSheetMockMvc.perform(post("/api/employee-time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeTimeSheet)))
            .andExpect(status().isBadRequest());

        List<EmployeeTimeSheet> employeeTimeSheetList = employeeTimeSheetRepository.findAll();
        assertThat(employeeTimeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeTimeSheets() throws Exception {
        // Initialize the database
        employeeTimeSheetRepository.saveAndFlush(employeeTimeSheet);

        // Get all the employeeTimeSheetList
        restEmployeeTimeSheetMockMvc.perform(get("/api/employee-time-sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeTimeSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].noOfWorkingDays").value(hasItem(DEFAULT_NO_OF_WORKING_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].noOfLeavs").value(hasItem(DEFAULT_NO_OF_LEAVS.intValue())))
            .andExpect(jsonPath("$.[*].noOfLop").value(hasItem(DEFAULT_NO_OF_LOP.intValue())))
            .andExpect(jsonPath("$.[*].noOfArearDays").value(hasItem(DEFAULT_NO_OF_AREAR_DAYS.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeTimeSheet() throws Exception {
        // Initialize the database
        employeeTimeSheetRepository.saveAndFlush(employeeTimeSheet);

        // Get the employeeTimeSheet
        restEmployeeTimeSheetMockMvc.perform(get("/api/employee-time-sheets/{id}", employeeTimeSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeTimeSheet.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.noOfWorkingDays").value(DEFAULT_NO_OF_WORKING_DAYS.intValue()))
            .andExpect(jsonPath("$.noOfLeavs").value(DEFAULT_NO_OF_LEAVS.intValue()))
            .andExpect(jsonPath("$.noOfLop").value(DEFAULT_NO_OF_LOP.intValue()))
            .andExpect(jsonPath("$.noOfArearDays").value(DEFAULT_NO_OF_AREAR_DAYS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeTimeSheet() throws Exception {
        // Get the employeeTimeSheet
        restEmployeeTimeSheetMockMvc.perform(get("/api/employee-time-sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeTimeSheet() throws Exception {
        // Initialize the database
        employeeTimeSheetService.save(employeeTimeSheet);

        int databaseSizeBeforeUpdate = employeeTimeSheetRepository.findAll().size();

        // Update the employeeTimeSheet
        EmployeeTimeSheet updatedEmployeeTimeSheet = employeeTimeSheetRepository.findById(employeeTimeSheet.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeTimeSheet are not directly saved in db
        em.detach(updatedEmployeeTimeSheet);
        updatedEmployeeTimeSheet
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .noOfWorkingDays(UPDATED_NO_OF_WORKING_DAYS)
            .noOfLeavs(UPDATED_NO_OF_LEAVS)
            .noOfLop(UPDATED_NO_OF_LOP)
            .noOfArearDays(UPDATED_NO_OF_AREAR_DAYS);

        restEmployeeTimeSheetMockMvc.perform(put("/api/employee-time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeTimeSheet)))
            .andExpect(status().isOk());

        // Validate the EmployeeTimeSheet in the database
        List<EmployeeTimeSheet> employeeTimeSheetList = employeeTimeSheetRepository.findAll();
        assertThat(employeeTimeSheetList).hasSize(databaseSizeBeforeUpdate);
        EmployeeTimeSheet testEmployeeTimeSheet = employeeTimeSheetList.get(employeeTimeSheetList.size() - 1);
        assertThat(testEmployeeTimeSheet.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testEmployeeTimeSheet.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testEmployeeTimeSheet.getNoOfWorkingDays()).isEqualTo(UPDATED_NO_OF_WORKING_DAYS);
        assertThat(testEmployeeTimeSheet.getNoOfLeavs()).isEqualTo(UPDATED_NO_OF_LEAVS);
        assertThat(testEmployeeTimeSheet.getNoOfLop()).isEqualTo(UPDATED_NO_OF_LOP);
        assertThat(testEmployeeTimeSheet.getNoOfArearDays()).isEqualTo(UPDATED_NO_OF_AREAR_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = employeeTimeSheetRepository.findAll().size();

        // Create the EmployeeTimeSheet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeTimeSheetMockMvc.perform(put("/api/employee-time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeTimeSheet)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTimeSheet in the database
        List<EmployeeTimeSheet> employeeTimeSheetList = employeeTimeSheetRepository.findAll();
        assertThat(employeeTimeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeTimeSheet() throws Exception {
        // Initialize the database
        employeeTimeSheetService.save(employeeTimeSheet);

        int databaseSizeBeforeDelete = employeeTimeSheetRepository.findAll().size();

        // Delete the employeeTimeSheet
        restEmployeeTimeSheetMockMvc.perform(delete("/api/employee-time-sheets/{id}", employeeTimeSheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeTimeSheet> employeeTimeSheetList = employeeTimeSheetRepository.findAll();
        assertThat(employeeTimeSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeTimeSheet.class);
        EmployeeTimeSheet employeeTimeSheet1 = new EmployeeTimeSheet();
        employeeTimeSheet1.setId(1L);
        EmployeeTimeSheet employeeTimeSheet2 = new EmployeeTimeSheet();
        employeeTimeSheet2.setId(employeeTimeSheet1.getId());
        assertThat(employeeTimeSheet1).isEqualTo(employeeTimeSheet2);
        employeeTimeSheet2.setId(2L);
        assertThat(employeeTimeSheet1).isNotEqualTo(employeeTimeSheet2);
        employeeTimeSheet1.setId(null);
        assertThat(employeeTimeSheet1).isNotEqualTo(employeeTimeSheet2);
    }
}
