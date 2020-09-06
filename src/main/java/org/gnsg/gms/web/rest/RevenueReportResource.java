package org.gnsg.gms.web.rest;

import org.gnsg.gms.domain.RevenueReport;
import org.gnsg.gms.service.RevenueReportService;
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
 * REST controller for managing {@link org.gnsg.gms.domain.RevenueReport}.
 */
@RestController
@RequestMapping("/api")
public class RevenueReportResource {

    private final Logger log = LoggerFactory.getLogger(RevenueReportResource.class);

    private static final String ENTITY_NAME = "revenueReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RevenueReportService revenueReportService;

    public RevenueReportResource(RevenueReportService revenueReportService) {
        this.revenueReportService = revenueReportService;
    }

    /**
     * {@code POST  /revenue-reports} : Create a new revenueReport.
     *
     * @param revenueReport the revenueReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new revenueReport, or with status {@code 400 (Bad Request)} if the revenueReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/revenue-reports")
    public ResponseEntity<RevenueReport> createRevenueReport(@RequestBody RevenueReport revenueReport) throws URISyntaxException {
        log.debug("REST request to save RevenueReport : {}", revenueReport);
        if (revenueReport.getId() != null) {
            throw new BadRequestAlertException("A new revenueReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RevenueReport result = revenueReportService.save(revenueReport);
        return ResponseEntity.created(new URI("/api/revenue-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /revenue-reports} : Updates an existing revenueReport.
     *
     * @param revenueReport the revenueReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revenueReport,
     * or with status {@code 400 (Bad Request)} if the revenueReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the revenueReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/revenue-reports")
    public ResponseEntity<RevenueReport> updateRevenueReport(@RequestBody RevenueReport revenueReport) throws URISyntaxException {
        log.debug("REST request to update RevenueReport : {}", revenueReport);
        if (revenueReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RevenueReport result = revenueReportService.save(revenueReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revenueReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /revenue-reports} : get all the revenueReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of revenueReports in body.
     */
    @GetMapping("/revenue-reports")
    public List<RevenueReport> getAllRevenueReports() {
        log.debug("REST request to get all RevenueReports");
        return revenueReportService.findAll();
    }

    /**
     * {@code GET  /revenue-reports/:id} : get the "id" revenueReport.
     *
     * @param id the id of the revenueReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the revenueReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/revenue-reports/{id}")
    public ResponseEntity<RevenueReport> getRevenueReport(@PathVariable Long id) {
        log.debug("REST request to get RevenueReport : {}", id);
        Optional<RevenueReport> revenueReport = revenueReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(revenueReport);
    }

    /**
     * {@code DELETE  /revenue-reports/:id} : delete the "id" revenueReport.
     *
     * @param id the id of the revenueReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/revenue-reports/{id}")
    public ResponseEntity<Void> deleteRevenueReport(@PathVariable Long id) {
        log.debug("REST request to delete RevenueReport : {}", id);

        revenueReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/revenue-reports?query=:query} : search for the revenueReport corresponding
     * to the query.
     *
     * @param query the query of the revenueReport search.
     * @return the result of the search.
     */
    @GetMapping("/_search/revenue-reports")
    public List<RevenueReport> searchRevenueReports(@RequestParam String query) {
        log.debug("REST request to search RevenueReports for query {}", query);
        return revenueReportService.search(query);
    }
}
