package org.gnsg.gms.web.rest;

import org.gnsg.gms.domain.ExpenseReport;
import org.gnsg.gms.service.ExpenseReportService;
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
 * REST controller for managing {@link org.gnsg.gms.domain.ExpenseReport}.
 */
@RestController
@RequestMapping("/api")
public class ExpenseReportResource {

    private final Logger log = LoggerFactory.getLogger(ExpenseReportResource.class);

    private static final String ENTITY_NAME = "expenseReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpenseReportService expenseReportService;

    public ExpenseReportResource(ExpenseReportService expenseReportService) {
        this.expenseReportService = expenseReportService;
    }

    /**
     * {@code POST  /expense-reports} : Create a new expenseReport.
     *
     * @param expenseReport the expenseReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expenseReport, or with status {@code 400 (Bad Request)} if the expenseReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expense-reports")
    public ResponseEntity<ExpenseReport> createExpenseReport(@RequestBody ExpenseReport expenseReport) throws URISyntaxException {
        log.debug("REST request to save ExpenseReport : {}", expenseReport);
        if (expenseReport.getId() != null) {
            throw new BadRequestAlertException("A new expenseReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpenseReport result = expenseReportService.save(expenseReport);
        return ResponseEntity.created(new URI("/api/expense-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expense-reports} : Updates an existing expenseReport.
     *
     * @param expenseReport the expenseReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenseReport,
     * or with status {@code 400 (Bad Request)} if the expenseReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expenseReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expense-reports")
    public ResponseEntity<ExpenseReport> updateExpenseReport(@RequestBody ExpenseReport expenseReport) throws URISyntaxException {
        log.debug("REST request to update ExpenseReport : {}", expenseReport);
        if (expenseReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpenseReport result = expenseReportService.save(expenseReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenseReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expense-reports} : get all the expenseReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expenseReports in body.
     */
    @GetMapping("/expense-reports")
    public List<ExpenseReport> getAllExpenseReports() {
        log.debug("REST request to get all ExpenseReports");
        return expenseReportService.findAll();
    }

    /**
     * {@code GET  /expense-reports/:id} : get the "id" expenseReport.
     *
     * @param id the id of the expenseReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expenseReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expense-reports/{id}")
    public ResponseEntity<ExpenseReport> getExpenseReport(@PathVariable Long id) {
        log.debug("REST request to get ExpenseReport : {}", id);
        Optional<ExpenseReport> expenseReport = expenseReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expenseReport);
    }

    /**
     * {@code DELETE  /expense-reports/:id} : delete the "id" expenseReport.
     *
     * @param id the id of the expenseReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expense-reports/{id}")
    public ResponseEntity<Void> deleteExpenseReport(@PathVariable Long id) {
        log.debug("REST request to delete ExpenseReport : {}", id);

        expenseReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/expense-reports?query=:query} : search for the expenseReport corresponding
     * to the query.
     *
     * @param query the query of the expenseReport search.
     * @return the result of the search.
     */
    @GetMapping("/_search/expense-reports")
    public List<ExpenseReport> searchExpenseReports(@RequestParam String query) {
        log.debug("REST request to search ExpenseReports for query {}", query);
        return expenseReportService.search(query);
    }
}
