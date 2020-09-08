package org.gnsg.gms.web.rest;

import org.gnsg.gms.GnsgapplicationApp;
import org.gnsg.gms.domain.PathReport;
import org.gnsg.gms.repository.PathReportRepository;
import org.gnsg.gms.repository.search.PathReportSearchRepository;
import org.gnsg.gms.service.PathReportService;

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

import org.gnsg.gms.domain.enumeration.PATHSEARCHBY;
import org.gnsg.gms.domain.enumeration.PROGTYPE;
/**
 * Integration tests for the {@link PathReportResource} REST controller.
 */
@SpringBootTest(classes = GnsgapplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PathReportResourceIT {

    private static final PATHSEARCHBY DEFAULT_SEARCH_BY = PATHSEARCHBY.ALL;
    private static final PATHSEARCHBY UPDATED_SEARCH_BY = PATHSEARCHBY.PATHI_SINGH_NAME;

    private static final PROGTYPE DEFAULT_PATH_TYPE = PROGTYPE.SEHAJ_PATH;
    private static final PROGTYPE UPDATED_PATH_TYPE = PROGTYPE.AKHAND_PATH;

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
    private PathReportRepository pathReportRepository;

    @Autowired
    private PathReportService pathReportService;

    /**
     * This repository is mocked in the org.gnsg.gms.repository.search test package.
     *
     * @see org.gnsg.gms.repository.search.PathReportSearchRepositoryMockConfiguration
     */
    @Autowired
    private PathReportSearchRepository mockPathReportSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPathReportMockMvc;

    private PathReport pathReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PathReport createEntity(EntityManager em) {
        PathReport pathReport = new PathReport()
            .searchBy(DEFAULT_SEARCH_BY)
            .pathType(DEFAULT_PATH_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .report(DEFAULT_REPORT)
            .reportContentType(DEFAULT_REPORT_CONTENT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return pathReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PathReport createUpdatedEntity(EntityManager em) {
        PathReport pathReport = new PathReport()
            .searchBy(UPDATED_SEARCH_BY)
            .pathType(UPDATED_PATH_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return pathReport;
    }

    @BeforeEach
    public void initTest() {
        pathReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createPathReport() throws Exception {
        int databaseSizeBeforeCreate = pathReportRepository.findAll().size();
        // Create the PathReport
        restPathReportMockMvc.perform(post("/api/path-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pathReport)))
            .andExpect(status().isCreated());

        // Validate the PathReport in the database
        List<PathReport> pathReportList = pathReportRepository.findAll();
        assertThat(pathReportList).hasSize(databaseSizeBeforeCreate + 1);
        PathReport testPathReport = pathReportList.get(pathReportList.size() - 1);
        assertThat(testPathReport.getSearchBy()).isEqualTo(DEFAULT_SEARCH_BY);
        assertThat(testPathReport.getPathType()).isEqualTo(DEFAULT_PATH_TYPE);
        assertThat(testPathReport.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPathReport.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPathReport.getReport()).isEqualTo(DEFAULT_REPORT);
        assertThat(testPathReport.getReportContentType()).isEqualTo(DEFAULT_REPORT_CONTENT_TYPE);
        assertThat(testPathReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPathReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPathReport.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testPathReport.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the PathReport in Elasticsearch
        verify(mockPathReportSearchRepository, times(1)).save(testPathReport);
    }

    @Test
    @Transactional
    public void createPathReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pathReportRepository.findAll().size();

        // Create the PathReport with an existing ID
        pathReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPathReportMockMvc.perform(post("/api/path-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pathReport)))
            .andExpect(status().isBadRequest());

        // Validate the PathReport in the database
        List<PathReport> pathReportList = pathReportRepository.findAll();
        assertThat(pathReportList).hasSize(databaseSizeBeforeCreate);

        // Validate the PathReport in Elasticsearch
        verify(mockPathReportSearchRepository, times(0)).save(pathReport);
    }


    @Test
    @Transactional
    public void getAllPathReports() throws Exception {
        // Initialize the database
        pathReportRepository.saveAndFlush(pathReport);

        // Get all the pathReportList
        restPathReportMockMvc.perform(get("/api/path-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pathReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].searchBy").value(hasItem(DEFAULT_SEARCH_BY.toString())))
            .andExpect(jsonPath("$.[*].pathType").value(hasItem(DEFAULT_PATH_TYPE.toString())))
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
    public void getPathReport() throws Exception {
        // Initialize the database
        pathReportRepository.saveAndFlush(pathReport);

        // Get the pathReport
        restPathReportMockMvc.perform(get("/api/path-reports/{id}", pathReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pathReport.getId().intValue()))
            .andExpect(jsonPath("$.searchBy").value(DEFAULT_SEARCH_BY.toString()))
            .andExpect(jsonPath("$.pathType").value(DEFAULT_PATH_TYPE.toString()))
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
    public void getNonExistingPathReport() throws Exception {
        // Get the pathReport
        restPathReportMockMvc.perform(get("/api/path-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePathReport() throws Exception {
        // Initialize the database
        pathReportService.save(pathReport);

        int databaseSizeBeforeUpdate = pathReportRepository.findAll().size();

        // Update the pathReport
        PathReport updatedPathReport = pathReportRepository.findById(pathReport.getId()).get();
        // Disconnect from session so that the updates on updatedPathReport are not directly saved in db
        em.detach(updatedPathReport);
        updatedPathReport
            .searchBy(UPDATED_SEARCH_BY)
            .pathType(UPDATED_PATH_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPathReportMockMvc.perform(put("/api/path-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPathReport)))
            .andExpect(status().isOk());

        // Validate the PathReport in the database
        List<PathReport> pathReportList = pathReportRepository.findAll();
        assertThat(pathReportList).hasSize(databaseSizeBeforeUpdate);
        PathReport testPathReport = pathReportList.get(pathReportList.size() - 1);
        assertThat(testPathReport.getSearchBy()).isEqualTo(UPDATED_SEARCH_BY);
        assertThat(testPathReport.getPathType()).isEqualTo(UPDATED_PATH_TYPE);
        assertThat(testPathReport.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPathReport.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPathReport.getReport()).isEqualTo(UPDATED_REPORT);
        assertThat(testPathReport.getReportContentType()).isEqualTo(UPDATED_REPORT_CONTENT_TYPE);
        assertThat(testPathReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPathReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPathReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testPathReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the PathReport in Elasticsearch
        verify(mockPathReportSearchRepository, times(2)).save(testPathReport);
    }

    @Test
    @Transactional
    public void updateNonExistingPathReport() throws Exception {
        int databaseSizeBeforeUpdate = pathReportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPathReportMockMvc.perform(put("/api/path-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pathReport)))
            .andExpect(status().isBadRequest());

        // Validate the PathReport in the database
        List<PathReport> pathReportList = pathReportRepository.findAll();
        assertThat(pathReportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PathReport in Elasticsearch
        verify(mockPathReportSearchRepository, times(0)).save(pathReport);
    }

    @Test
    @Transactional
    public void deletePathReport() throws Exception {
        // Initialize the database
        pathReportService.save(pathReport);

        int databaseSizeBeforeDelete = pathReportRepository.findAll().size();

        // Delete the pathReport
        restPathReportMockMvc.perform(delete("/api/path-reports/{id}", pathReport.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PathReport> pathReportList = pathReportRepository.findAll();
        assertThat(pathReportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PathReport in Elasticsearch
        verify(mockPathReportSearchRepository, times(1)).deleteById(pathReport.getId());
    }

    @Test
    @Transactional
    public void searchPathReport() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        pathReportService.save(pathReport);
        when(mockPathReportSearchRepository.search(queryStringQuery("id:" + pathReport.getId())))
            .thenReturn(Collections.singletonList(pathReport));

        // Search the pathReport
        restPathReportMockMvc.perform(get("/api/_search/path-reports?query=id:" + pathReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pathReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].searchBy").value(hasItem(DEFAULT_SEARCH_BY.toString())))
            .andExpect(jsonPath("$.[*].pathType").value(hasItem(DEFAULT_PATH_TYPE.toString())))
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
