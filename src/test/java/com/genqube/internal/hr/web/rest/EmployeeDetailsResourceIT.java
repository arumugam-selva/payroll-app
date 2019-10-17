package com.genqube.internal.hr.web.rest;

import com.genqube.internal.hr.PayRollApplicationApp;
import com.genqube.internal.hr.domain.EmployeeDetails;
import com.genqube.internal.hr.repository.EmployeeDetailsRepository;
import com.genqube.internal.hr.service.EmployeeDetailsService;
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
 * Integration tests for the {@link EmployeeDetailsResource} REST controller.
 */
@SpringBootTest(classes = PayRollApplicationApp.class)
public class EmployeeDetailsResourceIT {

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEPARTMENT = 1;
    private static final Integer UPDATED_DEPARTMENT = 2;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAN_NO = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

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

    private MockMvc restEmployeeDetailsMockMvc;

    private EmployeeDetails employeeDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeDetailsResource employeeDetailsResource = new EmployeeDetailsResource(employeeDetailsService);
        this.restEmployeeDetailsMockMvc = MockMvcBuilders.standaloneSetup(employeeDetailsResource)
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
    public static EmployeeDetails createEntity(EntityManager em) {
        EmployeeDetails employeeDetails = new EmployeeDetails()
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .designation(DEFAULT_DESIGNATION)
            .department(DEFAULT_DEPARTMENT)
            .dob(DEFAULT_DOB)
            .joiningDate(DEFAULT_JOINING_DATE)
            .panNo(DEFAULT_PAN_NO)
            .bankAccountNo(DEFAULT_BANK_ACCOUNT_NO)
            .gender(DEFAULT_GENDER)
            .bank(DEFAULT_BANK)
            .location(DEFAULT_LOCATION);
        return employeeDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDetails createUpdatedEntity(EntityManager em) {
        EmployeeDetails employeeDetails = new EmployeeDetails()
            .employeeId(UPDATED_EMPLOYEE_ID)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .designation(UPDATED_DESIGNATION)
            .department(UPDATED_DEPARTMENT)
            .dob(UPDATED_DOB)
            .joiningDate(UPDATED_JOINING_DATE)
            .panNo(UPDATED_PAN_NO)
            .bankAccountNo(UPDATED_BANK_ACCOUNT_NO)
            .gender(UPDATED_GENDER)
            .bank(UPDATED_BANK)
            .location(UPDATED_LOCATION);
        return employeeDetails;
    }

    @BeforeEach
    public void initTest() {
        employeeDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeDetails() throws Exception {
        int databaseSizeBeforeCreate = employeeDetailsRepository.findAll().size();

        // Create the EmployeeDetails
        restEmployeeDetailsMockMvc.perform(post("/api/employee-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDetails)))
            .andExpect(status().isCreated());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployeeDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployeeDetails.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployeeDetails.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testEmployeeDetails.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testEmployeeDetails.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testEmployeeDetails.getPanNo()).isEqualTo(DEFAULT_PAN_NO);
        assertThat(testEmployeeDetails.getBankAccountNo()).isEqualTo(DEFAULT_BANK_ACCOUNT_NO);
        assertThat(testEmployeeDetails.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployeeDetails.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testEmployeeDetails.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createEmployeeDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeDetailsRepository.findAll().size();

        // Create the EmployeeDetails with an existing ID
        employeeDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDetailsMockMvc.perform(post("/api/employee-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDetails)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList
        restEmployeeDetailsMockMvc.perform(get("/api/employee-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO)))
            .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }
    
    @Test
    @Transactional
    public void getEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get the employeeDetails
        restEmployeeDetailsMockMvc.perform(get("/api/employee-details/{id}", employeeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDetails.getId().intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.panNo").value(DEFAULT_PAN_NO))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeDetails() throws Exception {
        // Get the employeeDetails
        restEmployeeDetailsMockMvc.perform(get("/api/employee-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsService.save(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails
        EmployeeDetails updatedEmployeeDetails = employeeDetailsRepository.findById(employeeDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDetails are not directly saved in db
        em.detach(updatedEmployeeDetails);
        updatedEmployeeDetails
            .employeeId(UPDATED_EMPLOYEE_ID)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .designation(UPDATED_DESIGNATION)
            .department(UPDATED_DEPARTMENT)
            .dob(UPDATED_DOB)
            .joiningDate(UPDATED_JOINING_DATE)
            .panNo(UPDATED_PAN_NO)
            .bankAccountNo(UPDATED_BANK_ACCOUNT_NO)
            .gender(UPDATED_GENDER)
            .bank(UPDATED_BANK)
            .location(UPDATED_LOCATION);

        restEmployeeDetailsMockMvc.perform(put("/api/employee-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeDetails)))
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployeeDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployeeDetails.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeDetails.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEmployeeDetails.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testEmployeeDetails.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testEmployeeDetails.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testEmployeeDetails.getBankAccountNo()).isEqualTo(UPDATED_BANK_ACCOUNT_NO);
        assertThat(testEmployeeDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployeeDetails.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testEmployeeDetails.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Create the EmployeeDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc.perform(put("/api/employee-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDetails)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsService.save(employeeDetails);

        int databaseSizeBeforeDelete = employeeDetailsRepository.findAll().size();

        // Delete the employeeDetails
        restEmployeeDetailsMockMvc.perform(delete("/api/employee-details/{id}", employeeDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDetails.class);
        EmployeeDetails employeeDetails1 = new EmployeeDetails();
        employeeDetails1.setId(1L);
        EmployeeDetails employeeDetails2 = new EmployeeDetails();
        employeeDetails2.setId(employeeDetails1.getId());
        assertThat(employeeDetails1).isEqualTo(employeeDetails2);
        employeeDetails2.setId(2L);
        assertThat(employeeDetails1).isNotEqualTo(employeeDetails2);
        employeeDetails1.setId(null);
        assertThat(employeeDetails1).isNotEqualTo(employeeDetails2);
    }
}
