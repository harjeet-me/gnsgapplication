package org.gnsg.gms.web.rest;

import org.gnsg.gms.domain.DailyProgramReport;
import org.gnsg.gms.service.DailyProgramReportService;
import org.gnsg.gms.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.gnsg.gms.domain.DailyProgramReport}.
 */
@RestController
@RequestMapping("/api")
public class DailyProgramReportResource {

    private final Logger log = LoggerFactory.getLogger(DailyProgramReportResource.class);

    private static final String ENTITY_NAME = "dailyProgramReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyProgramReportService dailyProgramReportService;

    public DailyProgramReportResource(DailyProgramReportService dailyProgramReportService) {
        this.dailyProgramReportService = dailyProgramReportService;
    }

    /**
     * {@code POST  /daily-program-reports} : Create a new dailyProgramReport.
     *
     * @param dailyProgramReport the dailyProgramReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyProgramReport, or with status {@code 400 (Bad Request)} if the dailyProgramReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-program-reports")
    public ResponseEntity<DailyProgramReport> createDailyProgramReport(@RequestBody DailyProgramReport dailyProgramReport) throws URISyntaxException {
        log.debug("REST request to save DailyProgramReport : {}", dailyProgramReport);
        if (dailyProgramReport.getId() != null) {
            throw new BadRequestAlertException("A new dailyProgramReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyProgramReport result = dailyProgramReportService.save(dailyProgramReport);
        return ResponseEntity.created(new URI("/api/daily-program-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-program-reports} : Updates an existing dailyProgramReport.
     *
     * @param dailyProgramReport the dailyProgramReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyProgramReport,
     * or with status {@code 400 (Bad Request)} if the dailyProgramReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyProgramReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-program-reports")
    public ResponseEntity<DailyProgramReport> updateDailyProgramReport(@RequestBody DailyProgramReport dailyProgramReport) throws URISyntaxException {
        log.debug("REST request to update DailyProgramReport : {}", dailyProgramReport);
        if (dailyProgramReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DailyProgramReport result = dailyProgramReportService.save(dailyProgramReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyProgramReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /daily-program-reports} : get all the dailyProgramReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyProgramReports in body.
     */
    @GetMapping("/daily-program-reports")
    public List<DailyProgramReport> getAllDailyProgramReports() {
        log.debug("REST request to get all DailyProgramReports");
        return dailyProgramReportService.findAll();
    }

    /**
     * {@code GET  /daily-program-reports/:id} : get the "id" dailyProgramReport.
     *
     * @param id the id of the dailyProgramReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyProgramReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-program-reports/{id}")
    public ResponseEntity<DailyProgramReport> getDailyProgramReport(@PathVariable Long id) {
        log.debug("REST request to get DailyProgramReport : {}", id);
        Optional<DailyProgramReport> dailyProgramReport = dailyProgramReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyProgramReport);
    }

    /**
     * {@code DELETE  /daily-program-reports/:id} : delete the "id" dailyProgramReport.
     *
     * @param id the id of the dailyProgramReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-program-reports/{id}")
    public ResponseEntity<Void> deleteDailyProgramReport(@PathVariable Long id) {
        log.debug("REST request to delete DailyProgramReport : {}", id);

        dailyProgramReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/daily-program-reports?query=:query} : search for the dailyProgramReport corresponding
     * to the query.
     *
     * @param query the query of the dailyProgramReport search.
     * @return the result of the search.
     */
    @GetMapping("/_search/daily-program-reports")
    public List<DailyProgramReport> searchDailyProgramReports(@RequestParam String query) {
        log.debug("REST request to search DailyProgramReports for query {}", query);
        return dailyProgramReportService.search(query);
    }
}
