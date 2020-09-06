package org.gnsg.gms.web.rest;

import org.gnsg.gms.GnsgapplicationApp;
import org.gnsg.gms.domain.ExpenseReport;
import org.gnsg.gms.repository.ExpenseReportRepository;
import org.gnsg.gms.repository.search.ExpenseReportSearchRepository;
import org.gnsg.gms.service.ExpenseReportService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.gnsg.gms.domain.enumeration.EXPTYPE;
/**
 * Integration tests for the {@link ExpenseReportResource} REST controller.
 */
@SpringBootTest(classes = GnsgapplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExpenseReportResourceIT {

    private static final EXPTYPE DEFAULT_EXP_TYPE = EXPTYPE.INVOICE;
    private static final EXPTYPE UPDATED_EXP_TYPE = EXPTYPE.RECIEPT;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_REPORT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REPORT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REPORT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REPORT_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private ExpenseReportRepository expenseReportRepository;

    @Autowired
    private ExpenseReportService expenseReportService;

    /**
     * This repository is mocked in the org.gnsg.gms.repository.search test package.
     *
     * @see org.gnsg.gms.repository.search.ExpenseReportSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExpenseReportSearchRepository mockExpenseReportSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpenseReportMockMvc;

    private ExpenseReport expenseReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpenseReport createEntity(EntityManager em) {
        ExpenseReport expenseReport = new ExpenseReport()
            .expType(DEFAULT_EXP_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .report(DEFAULT_REPORT)
            .reportContentType(DEFAULT_REPORT_CONTENT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return expenseReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpenseReport createUpdatedEntity(EntityManager em) {
        ExpenseReport expenseReport = new ExpenseReport()
            .expType(UPDATED_EXP_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return expenseReport;
    }

    @BeforeEach
    public void initTest() {
        expenseReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpenseReport() throws Exception {
        int databaseSizeBeforeCreate = expenseReportRepository.findAll().size();
        // Create the ExpenseReport
        restExpenseReportMockMvc.perform(post("/api/expense-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expenseReport)))
            .andExpect(status().isCreated());

        // Validate the ExpenseReport in the database
        List<ExpenseReport> expenseReportList = expenseReportRepository.findAll();
        assertThat(expenseReportList).hasSize(databaseSizeBeforeCreate + 1);
        ExpenseReport testExpenseReport = expenseReportList.get(expenseReportList.size() - 1);
        assertThat(testExpenseReport.getExpType()).isEqualTo(DEFAULT_EXP_TYPE);
        assertThat(testExpenseReport.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExpenseReport.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExpenseReport.getReport()).isEqualTo(DEFAULT_REPORT);
        assertThat(testExpenseReport.getReportContentType()).isEqualTo(DEFAULT_REPORT_CONTENT_TYPE);
        assertThat(testExpenseReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testExpenseReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExpenseReport.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testExpenseReport.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the ExpenseReport in Elasticsearch
        verify(mockExpenseReportSearchRepository, times(1)).save(testExpenseReport);
    }

    @Test
    @Transactional
    public void createExpenseReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expenseReportRepository.findAll().size();

        // Create the ExpenseReport with an existing ID
        expenseReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpenseReportMockMvc.perform(post("/api/expense-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expenseReport)))
            .andExpect(status().isBadRequest());

        // Validate the ExpenseReport in the database
        List<ExpenseReport> expenseReportList = expenseReportRepository.findAll();
        assertThat(expenseReportList).hasSize(databaseSizeBeforeCreate);

        // Validate the ExpenseReport in Elasticsearch
        verify(mockExpenseReportSearchRepository, times(0)).save(expenseReport);
    }


    @Test
    @Transactional
    public void getAllExpenseReports() throws Exception {
        // Initialize the database
        expenseReportRepository.saveAndFlush(expenseReport);

        // Get all the expenseReportList
        restExpenseReportMockMvc.perform(get("/api/expense-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenseReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].expType").value(hasItem(DEFAULT_EXP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].reportContentType").value(hasItem(DEFAULT_REPORT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].report").value(hasItem(Base64Utils.encodeToString(DEFAULT_REPORT))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getExpenseReport() throws Exception {
        // Initialize the database
        expenseReportRepository.saveAndFlush(expenseReport);

        // Get the expenseReport
        restExpenseReportMockMvc.perform(get("/api/expense-reports/{id}", expenseReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expenseReport.getId().intValue()))
            .andExpect(jsonPath("$.expType").value(DEFAULT_EXP_TYPE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.reportContentType").value(DEFAULT_REPORT_CONTENT_TYPE))
            .andExpect(jsonPath("$.report").value(Base64Utils.encodeToString(DEFAULT_REPORT)))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingExpenseReport() throws Exception {
        // Get the expenseReport
        restExpenseReportMockMvc.perform(get("/api/expense-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpenseReport() throws Exception {
        // Initialize the database
        expenseReportService.save(expenseReport);

        int databaseSizeBeforeUpdate = expenseReportRepository.findAll().size();

        // Update the expenseReport
        ExpenseReport updatedExpenseReport = expenseReportRepository.findById(expenseReport.getId()).get();
        // Disconnect from session so that the updates on updatedExpenseReport are not directly saved in db
        em.detach(updatedExpenseReport);
        updatedExpenseReport
            .expType(UPDATED_EXP_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restExpenseReportMockMvc.perform(put("/api/expense-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExpenseReport)))
            .andExpect(status().isOk());

        // Validate the ExpenseReport in the database
        List<ExpenseReport> expenseReportList = expenseReportRepository.findAll();
        assertThat(expenseReportList).hasSize(databaseSizeBeforeUpdate);
        ExpenseReport testExpenseReport = expenseReportList.get(expenseReportList.size() - 1);
        assertThat(testExpenseReport.getExpType()).isEqualTo(UPDATED_EXP_TYPE);
        assertThat(testExpenseReport.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExpenseReport.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExpenseReport.getReport()).isEqualTo(UPDATED_REPORT);
        assertThat(testExpenseReport.getReportContentType()).isEqualTo(UPDATED_REPORT_CONTENT_TYPE);
        assertThat(testExpenseReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testExpenseReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExpenseReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testExpenseReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the ExpenseReport in Elasticsearch
        verify(mockExpenseReportSearchRepository, times(2)).save(testExpenseReport);
    }

    @Test
    @Transactional
    public void updateNonExistingExpenseReport() throws Exception {
        int databaseSizeBeforeUpdate = expenseReportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseReportMockMvc.perform(put("/api/expense-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expenseReport)))
            .andExpect(status().isBadRequest());

        // Validate the ExpenseReport in the database
        List<ExpenseReport> expenseReportList = expenseReportRepository.findAll();
        assertThat(expenseReportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ExpenseReport in Elasticsearch
        verify(mockExpenseReportSearchRepository, times(0)).save(expenseReport);
    }

    @Test
    @Transactional
    public void deleteExpenseReport() throws Exception {
        // Initialize the database
        expenseReportService.save(expenseReport);

        int databaseSizeBeforeDelete = expenseReportRepository.findAll().size();

        // Delete the expenseReport
        restExpenseReportMockMvc.perform(delete("/api/expense-reports/{id}", expenseReport.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExpenseReport> expenseReportList = expenseReportRepository.findAll();
        assertThat(expenseReportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ExpenseReport in Elasticsearch
        verify(mockExpenseReportSearchRepository, times(1)).deleteById(expenseReport.getId());
    }

    @Test
    @Transactional
    public void searchExpenseReport() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        expenseReportService.save(expenseReport);
        when(mockExpenseReportSearchRepository.search(queryStringQuery("id:" + expenseReport.getId())))
            .thenReturn(Collections.singletonList(expenseReport));

        // Search the expenseReport
        restExpenseReportMockMvc.perform(get("/api/_search/expense-reports?query=id:" + expenseReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenseReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].expType").value(hasItem(DEFAULT_EXP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].reportContentType").value(hasItem(DEFAULT_REPORT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].report").value(hasItem(Base64Utils.encodeToString(DEFAULT_REPORT))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
}
