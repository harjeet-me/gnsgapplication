package org.gnsg.gms.web.rest;

import org.gnsg.gms.GnsgapplicationApp;
import org.gnsg.gms.domain.DailyProgramReport;
import org.gnsg.gms.repository.DailyProgramReportRepository;
import org.gnsg.gms.repository.search.DailyProgramReportSearchRepository;
import org.gnsg.gms.service.DailyProgramReportService;

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

import org.gnsg.gms.domain.enumeration.EVENTTYPE;
/**
 * Integration tests for the {@link DailyProgramReportResource} REST controller.
 */
@SpringBootTest(classes = GnsgapplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DailyProgramReportResourceIT {

    private static final EVENTTYPE DEFAULT_PROGRAM_TYPE = EVENTTYPE.SUKHMANI_SAHIB;
    private static final EVENTTYPE UPDATED_PROGRAM_TYPE = EVENTTYPE.SUKHMANI_SAHIB_AT_HOME;

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
    private DailyProgramReportRepository dailyProgramReportRepository;

    @Autowired
    private DailyProgramReportService dailyProgramReportService;

    /**
     * This repository is mocked in the org.gnsg.gms.repository.search test package.
     *
     * @see org.gnsg.gms.repository.search.DailyProgramReportSearchRepositoryMockConfiguration
     */
    @Autowired
    private DailyProgramReportSearchRepository mockDailyProgramReportSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyProgramReportMockMvc;

    private DailyProgramReport dailyProgramReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyProgramReport createEntity(EntityManager em) {
        DailyProgramReport dailyProgramReport = new DailyProgramReport()
            .programType(DEFAULT_PROGRAM_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .report(DEFAULT_REPORT)
            .reportContentType(DEFAULT_REPORT_CONTENT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return dailyProgramReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyProgramReport createUpdatedEntity(EntityManager em) {
        DailyProgramReport dailyProgramReport = new DailyProgramReport()
            .programType(UPDATED_PROGRAM_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return dailyProgramReport;
    }

    @BeforeEach
    public void initTest() {
        dailyProgramReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createDailyProgramReport() throws Exception {
        int databaseSizeBeforeCreate = dailyProgramReportRepository.findAll().size();
        // Create the DailyProgramReport
        restDailyProgramReportMockMvc.perform(post("/api/daily-program-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyProgramReport)))
            .andExpect(status().isCreated());

        // Validate the DailyProgramReport in the database
        List<DailyProgramReport> dailyProgramReportList = dailyProgramReportRepository.findAll();
        assertThat(dailyProgramReportList).hasSize(databaseSizeBeforeCreate + 1);
        DailyProgramReport testDailyProgramReport = dailyProgramReportList.get(dailyProgramReportList.size() - 1);
        assertThat(testDailyProgramReport.getProgramType()).isEqualTo(DEFAULT_PROGRAM_TYPE);
        assertThat(testDailyProgramReport.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDailyProgramReport.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDailyProgramReport.getReport()).isEqualTo(DEFAULT_REPORT);
        assertThat(testDailyProgramReport.getReportContentType()).isEqualTo(DEFAULT_REPORT_CONTENT_TYPE);
        assertThat(testDailyProgramReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDailyProgramReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDailyProgramReport.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testDailyProgramReport.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the DailyProgramReport in Elasticsearch
        verify(mockDailyProgramReportSearchRepository, times(1)).save(testDailyProgramReport);
    }

    @Test
    @Transactional
    public void createDailyProgramReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dailyProgramReportRepository.findAll().size();

        // Create the DailyProgramReport with an existing ID
        dailyProgramReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyProgramReportMockMvc.perform(post("/api/daily-program-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyProgramReport)))
            .andExpect(status().isBadRequest());

        // Validate the DailyProgramReport in the database
        List<DailyProgramReport> dailyProgramReportList = dailyProgramReportRepository.findAll();
        assertThat(dailyProgramReportList).hasSize(databaseSizeBeforeCreate);

        // Validate the DailyProgramReport in Elasticsearch
        verify(mockDailyProgramReportSearchRepository, times(0)).save(dailyProgramReport);
    }


    @Test
    @Transactional
    public void getAllDailyProgramReports() throws Exception {
        // Initialize the database
        dailyProgramReportRepository.saveAndFlush(dailyProgramReport);

        // Get all the dailyProgramReportList
        restDailyProgramReportMockMvc.perform(get("/api/daily-program-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyProgramReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].programType").value(hasItem(DEFAULT_PROGRAM_TYPE.toString())))
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
    public void getDailyProgramReport() throws Exception {
        // Initialize the database
        dailyProgramReportRepository.saveAndFlush(dailyProgramReport);

        // Get the dailyProgramReport
        restDailyProgramReportMockMvc.perform(get("/api/daily-program-reports/{id}", dailyProgramReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyProgramReport.getId().intValue()))
            .andExpect(jsonPath("$.programType").value(DEFAULT_PROGRAM_TYPE.toString()))
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
    public void getNonExistingDailyProgramReport() throws Exception {
        // Get the dailyProgramReport
        restDailyProgramReportMockMvc.perform(get("/api/daily-program-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDailyProgramReport() throws Exception {
        // Initialize the database
        dailyProgramReportService.save(dailyProgramReport);

        int databaseSizeBeforeUpdate = dailyProgramReportRepository.findAll().size();

        // Update the dailyProgramReport
        DailyProgramReport updatedDailyProgramReport = dailyProgramReportRepository.findById(dailyProgramReport.getId()).get();
        // Disconnect from session so that the updates on updatedDailyProgramReport are not directly saved in db
        em.detach(updatedDailyProgramReport);
        updatedDailyProgramReport
            .programType(UPDATED_PROGRAM_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restDailyProgramReportMockMvc.perform(put("/api/daily-program-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDailyProgramReport)))
            .andExpect(status().isOk());

        // Validate the DailyProgramReport in the database
        List<DailyProgramReport> dailyProgramReportList = dailyProgramReportRepository.findAll();
        assertThat(dailyProgramReportList).hasSize(databaseSizeBeforeUpdate);
        DailyProgramReport testDailyProgramReport = dailyProgramReportList.get(dailyProgramReportList.size() - 1);
        assertThat(testDailyProgramReport.getProgramType()).isEqualTo(UPDATED_PROGRAM_TYPE);
        assertThat(testDailyProgramReport.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDailyProgramReport.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDailyProgramReport.getReport()).isEqualTo(UPDATED_REPORT);
        assertThat(testDailyProgramReport.getReportContentType()).isEqualTo(UPDATED_REPORT_CONTENT_TYPE);
        assertThat(testDailyProgramReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDailyProgramReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDailyProgramReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testDailyProgramReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the DailyProgramReport in Elasticsearch
        verify(mockDailyProgramReportSearchRepository, times(2)).save(testDailyProgramReport);
    }

    @Test
    @Transactional
    public void updateNonExistingDailyProgramReport() throws Exception {
        int databaseSizeBeforeUpdate = dailyProgramReportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyProgramReportMockMvc.perform(put("/api/daily-program-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyProgramReport)))
            .andExpect(status().isBadRequest());

        // Validate the DailyProgramReport in the database
        List<DailyProgramReport> dailyProgramReportList = dailyProgramReportRepository.findAll();
        assertThat(dailyProgramReportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DailyProgramReport in Elasticsearch
        verify(mockDailyProgramReportSearchRepository, times(0)).save(dailyProgramReport);
    }

    @Test
    @Transactional
    public void deleteDailyProgramReport() throws Exception {
        // Initialize the database
        dailyProgramReportService.save(dailyProgramReport);

        int databaseSizeBeforeDelete = dailyProgramReportRepository.findAll().size();

        // Delete the dailyProgramReport
        restDailyProgramReportMockMvc.perform(delete("/api/daily-program-reports/{id}", dailyProgramReport.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyProgramReport> dailyProgramReportList = dailyProgramReportRepository.findAll();
        assertThat(dailyProgramReportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DailyProgramReport in Elasticsearch
        verify(mockDailyProgramReportSearchRepository, times(1)).deleteById(dailyProgramReport.getId());
    }

    @Test
    @Transactional
    public void searchDailyProgramReport() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dailyProgramReportService.save(dailyProgramReport);
        when(mockDailyProgramReportSearchRepository.search(queryStringQuery("id:" + dailyProgramReport.getId())))
            .thenReturn(Collections.singletonList(dailyProgramReport));

        // Search the dailyProgramReport
        restDailyProgramReportMockMvc.perform(get("/api/_search/daily-program-reports?query=id:" + dailyProgramReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyProgramReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].programType").value(hasItem(DEFAULT_PROGRAM_TYPE.toString())))
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
