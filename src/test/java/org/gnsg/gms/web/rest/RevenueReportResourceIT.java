package org.gnsg.gms.web.rest;

import org.gnsg.gms.GnsgapplicationApp;
import org.gnsg.gms.domain.RevenueReport;
import org.gnsg.gms.repository.RevenueReportRepository;
import org.gnsg.gms.repository.search.RevenueReportSearchRepository;
import org.gnsg.gms.service.RevenueReportService;

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

import org.gnsg.gms.domain.enumeration.REVTYPE;
/**
 * Integration tests for the {@link RevenueReportResource} REST controller.
 */
@SpringBootTest(classes = GnsgapplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RevenueReportResourceIT {

    private static final REVTYPE DEFAULT_REV_TYPE = REVTYPE.PROGRAM;
    private static final REVTYPE UPDATED_REV_TYPE = REVTYPE.GOLAK;

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
    private RevenueReportRepository revenueReportRepository;

    @Autowired
    private RevenueReportService revenueReportService;

    /**
     * This repository is mocked in the org.gnsg.gms.repository.search test package.
     *
     * @see org.gnsg.gms.repository.search.RevenueReportSearchRepositoryMockConfiguration
     */
    @Autowired
    private RevenueReportSearchRepository mockRevenueReportSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRevenueReportMockMvc;

    private RevenueReport revenueReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RevenueReport createEntity(EntityManager em) {
        RevenueReport revenueReport = new RevenueReport()
            .revType(DEFAULT_REV_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .report(DEFAULT_REPORT)
            .reportContentType(DEFAULT_REPORT_CONTENT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return revenueReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RevenueReport createUpdatedEntity(EntityManager em) {
        RevenueReport revenueReport = new RevenueReport()
            .revType(UPDATED_REV_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return revenueReport;
    }

    @BeforeEach
    public void initTest() {
        revenueReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createRevenueReport() throws Exception {
        int databaseSizeBeforeCreate = revenueReportRepository.findAll().size();
        // Create the RevenueReport
        restRevenueReportMockMvc.perform(post("/api/revenue-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(revenueReport)))
            .andExpect(status().isCreated());

        // Validate the RevenueReport in the database
        List<RevenueReport> revenueReportList = revenueReportRepository.findAll();
        assertThat(revenueReportList).hasSize(databaseSizeBeforeCreate + 1);
        RevenueReport testRevenueReport = revenueReportList.get(revenueReportList.size() - 1);
        assertThat(testRevenueReport.getRevType()).isEqualTo(DEFAULT_REV_TYPE);
        assertThat(testRevenueReport.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRevenueReport.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRevenueReport.getReport()).isEqualTo(DEFAULT_REPORT);
        assertThat(testRevenueReport.getReportContentType()).isEqualTo(DEFAULT_REPORT_CONTENT_TYPE);
        assertThat(testRevenueReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRevenueReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRevenueReport.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testRevenueReport.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the RevenueReport in Elasticsearch
        verify(mockRevenueReportSearchRepository, times(1)).save(testRevenueReport);
    }

    @Test
    @Transactional
    public void createRevenueReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = revenueReportRepository.findAll().size();

        // Create the RevenueReport with an existing ID
        revenueReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRevenueReportMockMvc.perform(post("/api/revenue-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(revenueReport)))
            .andExpect(status().isBadRequest());

        // Validate the RevenueReport in the database
        List<RevenueReport> revenueReportList = revenueReportRepository.findAll();
        assertThat(revenueReportList).hasSize(databaseSizeBeforeCreate);

        // Validate the RevenueReport in Elasticsearch
        verify(mockRevenueReportSearchRepository, times(0)).save(revenueReport);
    }


    @Test
    @Transactional
    public void getAllRevenueReports() throws Exception {
        // Initialize the database
        revenueReportRepository.saveAndFlush(revenueReport);

        // Get all the revenueReportList
        restRevenueReportMockMvc.perform(get("/api/revenue-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revenueReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].revType").value(hasItem(DEFAULT_REV_TYPE.toString())))
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
    public void getRevenueReport() throws Exception {
        // Initialize the database
        revenueReportRepository.saveAndFlush(revenueReport);

        // Get the revenueReport
        restRevenueReportMockMvc.perform(get("/api/revenue-reports/{id}", revenueReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(revenueReport.getId().intValue()))
            .andExpect(jsonPath("$.revType").value(DEFAULT_REV_TYPE.toString()))
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
    public void getNonExistingRevenueReport() throws Exception {
        // Get the revenueReport
        restRevenueReportMockMvc.perform(get("/api/revenue-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRevenueReport() throws Exception {
        // Initialize the database
        revenueReportService.save(revenueReport);

        int databaseSizeBeforeUpdate = revenueReportRepository.findAll().size();

        // Update the revenueReport
        RevenueReport updatedRevenueReport = revenueReportRepository.findById(revenueReport.getId()).get();
        // Disconnect from session so that the updates on updatedRevenueReport are not directly saved in db
        em.detach(updatedRevenueReport);
        updatedRevenueReport
            .revType(UPDATED_REV_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restRevenueReportMockMvc.perform(put("/api/revenue-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRevenueReport)))
            .andExpect(status().isOk());

        // Validate the RevenueReport in the database
        List<RevenueReport> revenueReportList = revenueReportRepository.findAll();
        assertThat(revenueReportList).hasSize(databaseSizeBeforeUpdate);
        RevenueReport testRevenueReport = revenueReportList.get(revenueReportList.size() - 1);
        assertThat(testRevenueReport.getRevType()).isEqualTo(UPDATED_REV_TYPE);
        assertThat(testRevenueReport.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRevenueReport.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRevenueReport.getReport()).isEqualTo(UPDATED_REPORT);
        assertThat(testRevenueReport.getReportContentType()).isEqualTo(UPDATED_REPORT_CONTENT_TYPE);
        assertThat(testRevenueReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRevenueReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRevenueReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testRevenueReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the RevenueReport in Elasticsearch
        verify(mockRevenueReportSearchRepository, times(2)).save(testRevenueReport);
    }

    @Test
    @Transactional
    public void updateNonExistingRevenueReport() throws Exception {
        int databaseSizeBeforeUpdate = revenueReportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevenueReportMockMvc.perform(put("/api/revenue-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(revenueReport)))
            .andExpect(status().isBadRequest());

        // Validate the RevenueReport in the database
        List<RevenueReport> revenueReportList = revenueReportRepository.findAll();
        assertThat(revenueReportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RevenueReport in Elasticsearch
        verify(mockRevenueReportSearchRepository, times(0)).save(revenueReport);
    }

    @Test
    @Transactional
    public void deleteRevenueReport() throws Exception {
        // Initialize the database
        revenueReportService.save(revenueReport);

        int databaseSizeBeforeDelete = revenueReportRepository.findAll().size();

        // Delete the revenueReport
        restRevenueReportMockMvc.perform(delete("/api/revenue-reports/{id}", revenueReport.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RevenueReport> revenueReportList = revenueReportRepository.findAll();
        assertThat(revenueReportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RevenueReport in Elasticsearch
        verify(mockRevenueReportSearchRepository, times(1)).deleteById(revenueReport.getId());
    }

    @Test
    @Transactional
    public void searchRevenueReport() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        revenueReportService.save(revenueReport);
        when(mockRevenueReportSearchRepository.search(queryStringQuery("id:" + revenueReport.getId())))
            .thenReturn(Collections.singletonList(revenueReport));

        // Search the revenueReport
        restRevenueReportMockMvc.perform(get("/api/_search/revenue-reports?query=id:" + revenueReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revenueReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].revType").value(hasItem(DEFAULT_REV_TYPE.toString())))
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
