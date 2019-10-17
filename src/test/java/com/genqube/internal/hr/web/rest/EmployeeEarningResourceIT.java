package com.genqube.internal.hr.web.rest;

import com.genqube.internal.hr.PayRollApplicationApp;
import com.genqube.internal.hr.domain.EmployeeEarning;
import com.genqube.internal.hr.repository.EmployeeEarningRepository;
import com.genqube.internal.hr.service.EmployeeEarningService;
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
 * Integration tests for the {@link EmployeeEarningResource} REST controller.
 */
@SpringBootTest(classes = PayRollApplicationApp.class)
public class EmployeeEarningResourceIT {

    private static final Integer DEFAULT_EFFECTIVE_DATE = 1;
    private static final Integer UPDATED_EFFECTIVE_DATE = 2;

    private static final Long DEFAULT_BASIC = 1L;
    private static final Long UPDATED_BASIC = 2L;

    private static final Long DEFAULT_HRA = 1L;
    private static final Long UPDATED_HRA = 2L;

    private static final Long DEFAULT_CONVEYANCE = 1L;
    private static final Long UPDATED_CONVEYANCE = 2L;

    private static final Long DEFAULT_LEAVE_ENCASH = 1L;
    private static final Long UPDATED_LEAVE_ENCASH = 2L;

    private static final Long DEFAULT_SPECIAL_ALLOWANCE = 1L;
    private static final Long UPDATED_SPECIAL_ALLOWANCE = 2L;

    private static final Long DEFAULT_REIMBURSEMENT = 1L;
    private static final Long UPDATED_REIMBURSEMENT = 2L;

    @Autowired
    private EmployeeEarningRepository employeeEarningRepository;

    @Autowired
    private EmployeeEarningService employeeEarningService;

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

    private MockMvc restEmployeeEarningMockMvc;

    private EmployeeEarning employeeEarning;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeEarningResource employeeEarningResource = new EmployeeEarningResource(employeeEarningService);
        this.restEmployeeEarningMockMvc = MockMvcBuilders.standaloneSetup(employeeEarningResource)
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
    public static EmployeeEarning createEntity(EntityManager em) {
        EmployeeEarning employeeEarning = new EmployeeEarning()
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .basic(DEFAULT_BASIC)
            .hra(DEFAULT_HRA)
            .conveyance(DEFAULT_CONVEYANCE)
            .leaveEncash(DEFAULT_LEAVE_ENCASH)
            .specialAllowance(DEFAULT_SPECIAL_ALLOWANCE)
            .reimbursement(DEFAULT_REIMBURSEMENT);
        return employeeEarning;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeEarning createUpdatedEntity(EntityManager em) {
        EmployeeEarning employeeEarning = new EmployeeEarning()
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .basic(UPDATED_BASIC)
            .hra(UPDATED_HRA)
            .conveyance(UPDATED_CONVEYANCE)
            .leaveEncash(UPDATED_LEAVE_ENCASH)
            .specialAllowance(UPDATED_SPECIAL_ALLOWANCE)
            .reimbursement(UPDATED_REIMBURSEMENT);
        return employeeEarning;
    }

    @BeforeEach
    public void initTest() {
        employeeEarning = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeEarning() throws Exception {
        int databaseSizeBeforeCreate = employeeEarningRepository.findAll().size();

        // Create the EmployeeEarning
        restEmployeeEarningMockMvc.perform(post("/api/employee-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeEarning)))
            .andExpect(status().isCreated());

        // Validate the EmployeeEarning in the database
        List<EmployeeEarning> employeeEarningList = employeeEarningRepository.findAll();
        assertThat(employeeEarningList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeEarning testEmployeeEarning = employeeEarningList.get(employeeEarningList.size() - 1);
        assertThat(testEmployeeEarning.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testEmployeeEarning.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testEmployeeEarning.getHra()).isEqualTo(DEFAULT_HRA);
        assertThat(testEmployeeEarning.getConveyance()).isEqualTo(DEFAULT_CONVEYANCE);
        assertThat(testEmployeeEarning.getLeaveEncash()).isEqualTo(DEFAULT_LEAVE_ENCASH);
        assertThat(testEmployeeEarning.getSpecialAllowance()).isEqualTo(DEFAULT_SPECIAL_ALLOWANCE);
        assertThat(testEmployeeEarning.getReimbursement()).isEqualTo(DEFAULT_REIMBURSEMENT);
    }

    @Test
    @Transactional
    public void createEmployeeEarningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeEarningRepository.findAll().size();

        // Create the EmployeeEarning with an existing ID
        employeeEarning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeEarningMockMvc.perform(post("/api/employee-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeEarning)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEarning in the database
        List<EmployeeEarning> employeeEarningList = employeeEarningRepository.findAll();
        assertThat(employeeEarningList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeEarnings() throws Exception {
        // Initialize the database
        employeeEarningRepository.saveAndFlush(employeeEarning);

        // Get all the employeeEarningList
        restEmployeeEarningMockMvc.perform(get("/api/employee-earnings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeEarning.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE)))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].hra").value(hasItem(DEFAULT_HRA.intValue())))
            .andExpect(jsonPath("$.[*].conveyance").value(hasItem(DEFAULT_CONVEYANCE.intValue())))
            .andExpect(jsonPath("$.[*].leaveEncash").value(hasItem(DEFAULT_LEAVE_ENCASH.intValue())))
            .andExpect(jsonPath("$.[*].specialAllowance").value(hasItem(DEFAULT_SPECIAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].reimbursement").value(hasItem(DEFAULT_REIMBURSEMENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeEarning() throws Exception {
        // Initialize the database
        employeeEarningRepository.saveAndFlush(employeeEarning);

        // Get the employeeEarning
        restEmployeeEarningMockMvc.perform(get("/api/employee-earnings/{id}", employeeEarning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeEarning.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.hra").value(DEFAULT_HRA.intValue()))
            .andExpect(jsonPath("$.conveyance").value(DEFAULT_CONVEYANCE.intValue()))
            .andExpect(jsonPath("$.leaveEncash").value(DEFAULT_LEAVE_ENCASH.intValue()))
            .andExpect(jsonPath("$.specialAllowance").value(DEFAULT_SPECIAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.reimbursement").value(DEFAULT_REIMBURSEMENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeEarning() throws Exception {
        // Get the employeeEarning
        restEmployeeEarningMockMvc.perform(get("/api/employee-earnings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeEarning() throws Exception {
        // Initialize the database
        employeeEarningService.save(employeeEarning);

        int databaseSizeBeforeUpdate = employeeEarningRepository.findAll().size();

        // Update the employeeEarning
        EmployeeEarning updatedEmployeeEarning = employeeEarningRepository.findById(employeeEarning.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeEarning are not directly saved in db
        em.detach(updatedEmployeeEarning);
        updatedEmployeeEarning
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .basic(UPDATED_BASIC)
            .hra(UPDATED_HRA)
            .conveyance(UPDATED_CONVEYANCE)
            .leaveEncash(UPDATED_LEAVE_ENCASH)
            .specialAllowance(UPDATED_SPECIAL_ALLOWANCE)
            .reimbursement(UPDATED_REIMBURSEMENT);

        restEmployeeEarningMockMvc.perform(put("/api/employee-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeEarning)))
            .andExpect(status().isOk());

        // Validate the EmployeeEarning in the database
        List<EmployeeEarning> employeeEarningList = employeeEarningRepository.findAll();
        assertThat(employeeEarningList).hasSize(databaseSizeBeforeUpdate);
        EmployeeEarning testEmployeeEarning = employeeEarningList.get(employeeEarningList.size() - 1);
        assertThat(testEmployeeEarning.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testEmployeeEarning.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testEmployeeEarning.getHra()).isEqualTo(UPDATED_HRA);
        assertThat(testEmployeeEarning.getConveyance()).isEqualTo(UPDATED_CONVEYANCE);
        assertThat(testEmployeeEarning.getLeaveEncash()).isEqualTo(UPDATED_LEAVE_ENCASH);
        assertThat(testEmployeeEarning.getSpecialAllowance()).isEqualTo(UPDATED_SPECIAL_ALLOWANCE);
        assertThat(testEmployeeEarning.getReimbursement()).isEqualTo(UPDATED_REIMBURSEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeEarning() throws Exception {
        int databaseSizeBeforeUpdate = employeeEarningRepository.findAll().size();

        // Create the EmployeeEarning

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeEarningMockMvc.perform(put("/api/employee-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeEarning)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeEarning in the database
        List<EmployeeEarning> employeeEarningList = employeeEarningRepository.findAll();
        assertThat(employeeEarningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeEarning() throws Exception {
        // Initialize the database
        employeeEarningService.save(employeeEarning);

        int databaseSizeBeforeDelete = employeeEarningRepository.findAll().size();

        // Delete the employeeEarning
        restEmployeeEarningMockMvc.perform(delete("/api/employee-earnings/{id}", employeeEarning.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeEarning> employeeEarningList = employeeEarningRepository.findAll();
        assertThat(employeeEarningList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeEarning.class);
        EmployeeEarning employeeEarning1 = new EmployeeEarning();
        employeeEarning1.setId(1L);
        EmployeeEarning employeeEarning2 = new EmployeeEarning();
        employeeEarning2.setId(employeeEarning1.getId());
        assertThat(employeeEarning1).isEqualTo(employeeEarning2);
        employeeEarning2.setId(2L);
        assertThat(employeeEarning1).isNotEqualTo(employeeEarning2);
        employeeEarning1.setId(null);
        assertThat(employeeEarning1).isNotEqualTo(employeeEarning2);
    }
}
