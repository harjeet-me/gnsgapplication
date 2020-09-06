package org.gnsg.gms.web.rest;

import org.gnsg.gms.domain.PathReport;
import org.gnsg.gms.service.PathReportService;
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
 * REST controller for managing {@link org.gnsg.gms.domain.PathReport}.
 */
@RestController
@RequestMapping("/api")
public class PathReportResource {

    private final Logger log = LoggerFactory.getLogger(PathReportResource.class);

    private static final String ENTITY_NAME = "pathReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PathReportService pathReportService;

    public PathReportResource(PathReportService pathReportService) {
        this.pathReportService = pathReportService;
    }

    /**
     * {@code POST  /path-reports} : Create a new pathReport.
     *
     * @param pathReport the pathReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pathReport, or with status {@code 400 (Bad Request)} if the pathReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/path-reports")
    public ResponseEntity<PathReport> createPathReport(@RequestBody PathReport pathReport) throws URISyntaxException {
        log.debug("REST request to save PathReport : {}", pathReport);
        if (pathReport.getId() != null) {
            throw new BadRequestAlertException("A new pathReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PathReport result = pathReportService.save(pathReport);
        return ResponseEntity.created(new URI("/api/path-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /path-reports} : Updates an existing pathReport.
     *
     * @param pathReport the pathReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pathReport,
     * or with status {@code 400 (Bad Request)} if the pathReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pathReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/path-reports")
    public ResponseEntity<PathReport> updatePathReport(@RequestBody PathReport pathReport) throws URISyntaxException {
        log.debug("REST request to update PathReport : {}", pathReport);
        if (pathReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PathReport result = pathReportService.save(pathReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pathReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /path-reports} : get all the pathReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pathReports in body.
     */
    @GetMapping("/path-reports")
    public List<PathReport> getAllPathReports() {
        log.debug("REST request to get all PathReports");
        return pathReportService.findAll();
    }

    /**
     * {@code GET  /path-reports/:id} : get the "id" pathReport.
     *
     * @param id the id of the pathReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pathReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/path-reports/{id}")
    public ResponseEntity<PathReport> getPathReport(@PathVariable Long id) {
        log.debug("REST request to get PathReport : {}", id);
        Optional<PathReport> pathReport = pathReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pathReport);
    }

    /**
     * {@code DELETE  /path-reports/:id} : delete the "id" pathReport.
     *
     * @param id the id of the pathReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/path-reports/{id}")
    public ResponseEntity<Void> deletePathReport(@PathVariable Long id) {
        log.debug("REST request to delete PathReport : {}", id);

        pathReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/path-reports?query=:query} : search for the pathReport corresponding
     * to the query.
     *
     * @param query the query of the pathReport search.
     * @return the result of the search.
     */
    @GetMapping("/_search/path-reports")
    public List<PathReport> searchPathReports(@RequestParam String query) {
        log.debug("REST request to search PathReports for query {}", query);
        return pathReportService.search(query);
    }
}
