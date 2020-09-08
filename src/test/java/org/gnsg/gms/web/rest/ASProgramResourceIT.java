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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.gnsg.gms.GnsgapplicationApp;
import org.gnsg.gms.domain.ASProgram;
import org.gnsg.gms.domain.enumeration.EventStatus;
import org.gnsg.gms.domain.enumeration.PROGTYPE;
import org.gnsg.gms.repository.ASProgramRepository;
import org.gnsg.gms.repository.search.ASProgramSearchRepository;
import org.gnsg.gms.service.ASProgramService;
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
 * Integration tests for the {@link ASProgramResource} REST controller.
 */
@SpringBootTest(classes = GnsgapplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ASProgramResourceIT {
    private static final PROGTYPE DEFAULT_PROGRAM = PROGTYPE.SEHAJ_PATH;
    private static final PROGTYPE UPDATED_PROGRAM = PROGTYPE.AKHAND_PATH;

    private static final String DEFAULT_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Instant DEFAULT_BOOKING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOKING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final EventStatus DEFAULT_STATUS = EventStatus.BOOKED;
    private static final EventStatus UPDATED_STATUS = EventStatus.COMPLETED;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private ASProgramRepository aSProgramRepository;

    @Mock
    private ASProgramRepository aSProgramRepositoryMock;

    @Mock
    private ASProgramService aSProgramServiceMock;

    @Autowired
    private ASProgramService aSProgramService;

    /**
     * This repository is mocked in the org.gnsg.gms.repository.search test package.
     *
     * @see org.gnsg.gms.repository.search.ASProgramSearchRepositoryMockConfiguration
     */
    @Autowired
    private ASProgramSearchRepository mockASProgramSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restASProgramMockMvc;

    private ASProgram aSProgram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ASProgram createEntity(EntityManager em) {
        ASProgram aSProgram = new ASProgram()
            .program(DEFAULT_PROGRAM)
            .family(DEFAULT_FAMILY)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .address(DEFAULT_ADDRESS)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .remark(DEFAULT_REMARK)
            .bookingDate(DEFAULT_BOOKING_DATE)
            .desc(DEFAULT_DESC)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return aSProgram;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ASProgram createUpdatedEntity(EntityManager em) {
        ASProgram aSProgram = new ASProgram()
            .program(UPDATED_PROGRAM)
            .family(UPDATED_FAMILY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .remark(UPDATED_REMARK)
            .bookingDate(UPDATED_BOOKING_DATE)
            .desc(UPDATED_DESC)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return aSProgram;
    }

    @BeforeEach
    public void initTest() {
        aSProgram = createEntity(em);
    }

    @Test
    @Transactional
    public void createASProgram() throws Exception {
        int databaseSizeBeforeCreate = aSProgramRepository.findAll().size();
        // Create the ASProgram
        restASProgramMockMvc
            .perform(
                post("/api/as-programs")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aSProgram))
            )
            .andExpect(status().isCreated());

        // Validate the ASProgram in the database
        List<ASProgram> aSProgramList = aSProgramRepository.findAll();
        assertThat(aSProgramList).hasSize(databaseSizeBeforeCreate + 1);
        ASProgram testASProgram = aSProgramList.get(aSProgramList.size() - 1);
        assertThat(testASProgram.getProgram()).isEqualTo(DEFAULT_PROGRAM);
        assertThat(testASProgram.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testASProgram.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testASProgram.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testASProgram.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testASProgram.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testASProgram.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testASProgram.getBookingDate()).isEqualTo(DEFAULT_BOOKING_DATE);
        assertThat(testASProgram.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testASProgram.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testASProgram.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testASProgram.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testASProgram.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testASProgram.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the ASProgram in Elasticsearch
        verify(mockASProgramSearchRepository, times(1)).save(testASProgram);
    }

    @Test
    @Transactional
    public void createASProgramWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aSProgramRepository.findAll().size();

        // Create the ASProgram with an existing ID
        aSProgram.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restASProgramMockMvc
            .perform(
                post("/api/as-programs")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aSProgram))
            )
            .andExpect(status().isBadRequest());

        // Validate the ASProgram in the database
        List<ASProgram> aSProgramList = aSProgramRepository.findAll();
        assertThat(aSProgramList).hasSize(databaseSizeBeforeCreate);

        // Validate the ASProgram in Elasticsearch
        verify(mockASProgramSearchRepository, times(0)).save(aSProgram);
    }

    @Test
    @Transactional
    public void getAllASPrograms() throws Exception {
        // Initialize the database
        aSProgramRepository.saveAndFlush(aSProgram);

        // Get all the aSProgramList
        restASProgramMockMvc
            .perform(get("/api/as-programs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aSProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())))
            .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].bookingDate").value(hasItem(DEFAULT_BOOKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllASProgramsWithEagerRelationshipsIsEnabled() throws Exception {
        when(aSProgramServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restASProgramMockMvc.perform(get("/api/as-programs?eagerload=true")).andExpect(status().isOk());

        verify(aSProgramServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllASProgramsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(aSProgramServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restASProgramMockMvc.perform(get("/api/as-programs?eagerload=true")).andExpect(status().isOk());

        verify(aSProgramServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getASProgram() throws Exception {
        // Initialize the database
        aSProgramRepository.saveAndFlush(aSProgram);

        // Get the aSProgram
        restASProgramMockMvc
            .perform(get("/api/as-programs/{id}", aSProgram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aSProgram.getId().intValue()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()))
            .andExpect(jsonPath("$.family").value(DEFAULT_FAMILY))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.bookingDate").value(DEFAULT_BOOKING_DATE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingASProgram() throws Exception {
        // Get the aSProgram
        restASProgramMockMvc.perform(get("/api/as-programs/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateASProgram() throws Exception {
        // Initialize the database
        aSProgramService.save(aSProgram);

        int databaseSizeBeforeUpdate = aSProgramRepository.findAll().size();

        // Update the aSProgram
        ASProgram updatedASProgram = aSProgramRepository.findById(aSProgram.getId()).get();
        // Disconnect from session so that the updates on updatedASProgram are not directly saved in db
        em.detach(updatedASProgram);
        updatedASProgram
            .program(UPDATED_PROGRAM)
            .family(UPDATED_FAMILY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .remark(UPDATED_REMARK)
            .bookingDate(UPDATED_BOOKING_DATE)
            .desc(UPDATED_DESC)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restASProgramMockMvc
            .perform(
                put("/api/as-programs")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedASProgram))
            )
            .andExpect(status().isOk());

        // Validate the ASProgram in the database
        List<ASProgram> aSProgramList = aSProgramRepository.findAll();
        assertThat(aSProgramList).hasSize(databaseSizeBeforeUpdate);
        ASProgram testASProgram = aSProgramList.get(aSProgramList.size() - 1);
        assertThat(testASProgram.getProgram()).isEqualTo(UPDATED_PROGRAM);
        assertThat(testASProgram.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testASProgram.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testASProgram.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testASProgram.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testASProgram.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testASProgram.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testASProgram.getBookingDate()).isEqualTo(UPDATED_BOOKING_DATE);
        assertThat(testASProgram.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testASProgram.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testASProgram.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testASProgram.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testASProgram.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testASProgram.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the ASProgram in Elasticsearch
        verify(mockASProgramSearchRepository, times(2)).save(testASProgram);
    }

    @Test
    @Transactional
    public void updateNonExistingASProgram() throws Exception {
        int databaseSizeBeforeUpdate = aSProgramRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restASProgramMockMvc
            .perform(
                put("/api/as-programs")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aSProgram))
            )
            .andExpect(status().isBadRequest());

        // Validate the ASProgram in the database
        List<ASProgram> aSProgramList = aSProgramRepository.findAll();
        assertThat(aSProgramList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ASProgram in Elasticsearch
        verify(mockASProgramSearchRepository, times(0)).save(aSProgram);
    }

    @Test
    @Transactional
    public void deleteASProgram() throws Exception {
        // Initialize the database
        aSProgramService.save(aSProgram);

        int databaseSizeBeforeDelete = aSProgramRepository.findAll().size();

        // Delete the aSProgram
        restASProgramMockMvc
            .perform(delete("/api/as-programs/{id}", aSProgram.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ASProgram> aSProgramList = aSProgramRepository.findAll();
        assertThat(aSProgramList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ASProgram in Elasticsearch
        verify(mockASProgramSearchRepository, times(1)).deleteById(aSProgram.getId());
    }

    @Test
    @Transactional
    public void searchASProgram() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        aSProgramService.save(aSProgram);
        when(mockASProgramSearchRepository.search(queryStringQuery("id:" + aSProgram.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(aSProgram), PageRequest.of(0, 1), 1));

        // Search the aSProgram
        restASProgramMockMvc
            .perform(get("/api/_search/as-programs?query=id:" + aSProgram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aSProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())))
            .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].bookingDate").value(hasItem(DEFAULT_BOOKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
}
