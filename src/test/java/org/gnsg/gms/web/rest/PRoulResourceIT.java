package org.gnsg.gms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.gnsg.gms.GnsgapplicationApp;
import org.gnsg.gms.domain.PRoul;
import org.gnsg.gms.repository.PRoulRepository;
import org.gnsg.gms.repository.search.PRoulSearchRepository;
import org.gnsg.gms.service.PRoulService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PRoulResource} REST controller.
 */
@SpringBootTest(classes = GnsgapplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PRoulResourceIT {
    private static final String DEFAULT_PATHI_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATHI_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_ROUL = 1D;
    private static final Double UPDATED_TOTAL_ROUL = 2D;

    private static final Double DEFAULT_TOTAL_AMT = 1D;
    private static final Double UPDATED_TOTAL_AMT = 2D;

    private static final LocalDate DEFAULT_BHOG_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BHOG_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private PRoulRepository pRoulRepository;

    @Autowired
    private PRoulService pRoulService;

    /**
     * This repository is mocked in the org.gnsg.gms.repository.search test package.
     *
     * @see org.gnsg.gms.repository.search.PRoulSearchRepositoryMockConfiguration
     */
    @Autowired
    private PRoulSearchRepository mockPRoulSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPRoulMockMvc;

    private PRoul pRoul;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRoul createEntity(EntityManager em) {
        PRoul pRoul = new PRoul()
            .pathiName(DEFAULT_PATHI_NAME)
            .desc(DEFAULT_DESC)
            .totalRoul(DEFAULT_TOTAL_ROUL)
            .totalAmt(DEFAULT_TOTAL_AMT)
            .bhogDate(DEFAULT_BHOG_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return pRoul;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRoul createUpdatedEntity(EntityManager em) {
        PRoul pRoul = new PRoul()
            .pathiName(UPDATED_PATHI_NAME)
            .desc(UPDATED_DESC)
            .totalRoul(UPDATED_TOTAL_ROUL)
            .totalAmt(UPDATED_TOTAL_AMT)
            .bhogDate(UPDATED_BHOG_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return pRoul;
    }

    @BeforeEach
    public void initTest() {
        pRoul = createEntity(em);
    }

    @Test
    @Transactional
    public void createPRoul() throws Exception {
        int databaseSizeBeforeCreate = pRoulRepository.findAll().size();
        // Create the PRoul
        restPRoulMockMvc
            .perform(
                post("/api/p-rouls").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRoul))
            )
            .andExpect(status().isCreated());

        // Validate the PRoul in the database
        List<PRoul> pRoulList = pRoulRepository.findAll();
        assertThat(pRoulList).hasSize(databaseSizeBeforeCreate + 1);
        PRoul testPRoul = pRoulList.get(pRoulList.size() - 1);
        assertThat(testPRoul.getPathiName()).isEqualTo(DEFAULT_PATHI_NAME);
        assertThat(testPRoul.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPRoul.getTotalRoul()).isEqualTo(DEFAULT_TOTAL_ROUL);
        assertThat(testPRoul.getTotalAmt()).isEqualTo(DEFAULT_TOTAL_AMT);
        assertThat(testPRoul.getBhogDate()).isEqualTo(DEFAULT_BHOG_DATE);
        assertThat(testPRoul.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPRoul.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPRoul.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testPRoul.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the PRoul in Elasticsearch
        verify(mockPRoulSearchRepository, times(1)).save(testPRoul);
    }

    @Test
    @Transactional
    public void createPRoulWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pRoulRepository.findAll().size();

        // Create the PRoul with an existing ID
        pRoul.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPRoulMockMvc
            .perform(
                post("/api/p-rouls").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRoul))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRoul in the database
        List<PRoul> pRoulList = pRoulRepository.findAll();
        assertThat(pRoulList).hasSize(databaseSizeBeforeCreate);

        // Validate the PRoul in Elasticsearch
        verify(mockPRoulSearchRepository, times(0)).save(pRoul);
    }

    @Test
    @Transactional
    public void getAllPRouls() throws Exception {
        // Initialize the database
        pRoulRepository.saveAndFlush(pRoul);

        // Get all the pRoulList
        restPRoulMockMvc
            .perform(get("/api/p-rouls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pRoul.getId().intValue())))
            .andExpect(jsonPath("$.[*].pathiName").value(hasItem(DEFAULT_PATHI_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].totalRoul").value(hasItem(DEFAULT_TOTAL_ROUL.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmt").value(hasItem(DEFAULT_TOTAL_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].bhogDate").value(hasItem(DEFAULT_BHOG_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    public void getPRoul() throws Exception {
        // Initialize the database
        pRoulRepository.saveAndFlush(pRoul);

        // Get the pRoul
        restPRoulMockMvc
            .perform(get("/api/p-rouls/{id}", pRoul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pRoul.getId().intValue()))
            .andExpect(jsonPath("$.pathiName").value(DEFAULT_PATHI_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.totalRoul").value(DEFAULT_TOTAL_ROUL.doubleValue()))
            .andExpect(jsonPath("$.totalAmt").value(DEFAULT_TOTAL_AMT.doubleValue()))
            .andExpect(jsonPath("$.bhogDate").value(DEFAULT_BHOG_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingPRoul() throws Exception {
        // Get the pRoul
        restPRoulMockMvc.perform(get("/api/p-rouls/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePRoul() throws Exception {
        // Initialize the database
        pRoulService.save(pRoul);

        int databaseSizeBeforeUpdate = pRoulRepository.findAll().size();

        // Update the pRoul
        PRoul updatedPRoul = pRoulRepository.findById(pRoul.getId()).get();
        // Disconnect from session so that the updates on updatedPRoul are not directly saved in db
        em.detach(updatedPRoul);
        updatedPRoul
            .pathiName(UPDATED_PATHI_NAME)
            .desc(UPDATED_DESC)
            .totalRoul(UPDATED_TOTAL_ROUL)
            .totalAmt(UPDATED_TOTAL_AMT)
            .bhogDate(UPDATED_BHOG_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPRoulMockMvc
            .perform(
                put("/api/p-rouls")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPRoul))
            )
            .andExpect(status().isOk());

        // Validate the PRoul in the database
        List<PRoul> pRoulList = pRoulRepository.findAll();
        assertThat(pRoulList).hasSize(databaseSizeBeforeUpdate);
        PRoul testPRoul = pRoulList.get(pRoulList.size() - 1);
        assertThat(testPRoul.getPathiName()).isEqualTo(UPDATED_PATHI_NAME);
        assertThat(testPRoul.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPRoul.getTotalRoul()).isEqualTo(UPDATED_TOTAL_ROUL);
        assertThat(testPRoul.getTotalAmt()).isEqualTo(UPDATED_TOTAL_AMT);
        assertThat(testPRoul.getBhogDate()).isEqualTo(UPDATED_BHOG_DATE);
        assertThat(testPRoul.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPRoul.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPRoul.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testPRoul.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the PRoul in Elasticsearch
        verify(mockPRoulSearchRepository, times(2)).save(testPRoul);
    }

    @Test
    @Transactional
    public void updateNonExistingPRoul() throws Exception {
        int databaseSizeBeforeUpdate = pRoulRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRoulMockMvc
            .perform(
                put("/api/p-rouls").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRoul))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRoul in the database
        List<PRoul> pRoulList = pRoulRepository.findAll();
        assertThat(pRoulList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PRoul in Elasticsearch
        verify(mockPRoulSearchRepository, times(0)).save(pRoul);
    }

    @Test
    @Transactional
    public void deletePRoul() throws Exception {
        // Initialize the database
        pRoulService.save(pRoul);

        int databaseSizeBeforeDelete = pRoulRepository.findAll().size();

        // Delete the pRoul
        restPRoulMockMvc
            .perform(delete("/api/p-rouls/{id}", pRoul.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PRoul> pRoulList = pRoulRepository.findAll();
        assertThat(pRoulList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PRoul in Elasticsearch
        verify(mockPRoulSearchRepository, times(1)).deleteById(pRoul.getId());
    }

    @Test
    @Transactional
    public void searchPRoul() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        pRoulService.save(pRoul);
        when(mockPRoulSearchRepository.search(queryStringQuery("id:" + pRoul.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pRoul), PageRequest.of(0, 1), 1));

        // Search the pRoul
        restPRoulMockMvc
            .perform(get("/api/_search/p-rouls?query=id:" + pRoul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pRoul.getId().intValue())))
            .andExpect(jsonPath("$.[*].pathiName").value(hasItem(DEFAULT_PATHI_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].totalRoul").value(hasItem(DEFAULT_TOTAL_ROUL.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmt").value(hasItem(DEFAULT_TOTAL_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].bhogDate").value(hasItem(DEFAULT_BHOG_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
}
