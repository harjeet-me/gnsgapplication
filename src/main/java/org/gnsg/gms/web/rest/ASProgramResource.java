package org.gnsg.gms.web.rest;

import org.gnsg.gms.domain.ASProgram;
import org.gnsg.gms.service.ASProgramService;
import org.gnsg.gms.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.gnsg.gms.domain.ASProgram}.
 */
@RestController
@RequestMapping("/api")
public class ASProgramResource {

    private final Logger log = LoggerFactory.getLogger(ASProgramResource.class);

    private static final String ENTITY_NAME = "aSProgram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ASProgramService aSProgramService;

    public ASProgramResource(ASProgramService aSProgramService) {
        this.aSProgramService = aSProgramService;
    }

    /**
     * {@code POST  /as-programs} : Create a new aSProgram.
     *
     * @param aSProgram the aSProgram to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aSProgram, or with status {@code 400 (Bad Request)} if the aSProgram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/as-programs")
    public ResponseEntity<ASProgram> createASProgram(@RequestBody ASProgram aSProgram) throws URISyntaxException {
        log.debug("REST request to save ASProgram : {}", aSProgram);
        if (aSProgram.getId() != null) {
            throw new BadRequestAlertException("A new aSProgram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ASProgram result = aSProgramService.save(aSProgram);
        return ResponseEntity.created(new URI("/api/as-programs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /as-programs} : Updates an existing aSProgram.
     *
     * @param aSProgram the aSProgram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aSProgram,
     * or with status {@code 400 (Bad Request)} if the aSProgram is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aSProgram couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/as-programs")
    public ResponseEntity<ASProgram> updateASProgram(@RequestBody ASProgram aSProgram) throws URISyntaxException {
        log.debug("REST request to update ASProgram : {}", aSProgram);
        if (aSProgram.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ASProgram result = aSProgramService.save(aSProgram);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aSProgram.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /as-programs} : get all the aSPrograms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aSPrograms in body.
     */
    @GetMapping("/as-programs")
    public ResponseEntity<List<ASProgram>> getAllASPrograms(Pageable pageable) {
        log.debug("REST request to get a page of ASPrograms");
        Page<ASProgram> page = aSProgramService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /as-programs/:id} : get the "id" aSProgram.
     *
     * @param id the id of the aSProgram to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aSProgram, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/as-programs/{id}")
    public ResponseEntity<ASProgram> getASProgram(@PathVariable Long id) {
        log.debug("REST request to get ASProgram : {}", id);
        Optional<ASProgram> aSProgram = aSProgramService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aSProgram);
    }

    /**
     * {@code DELETE  /as-programs/:id} : delete the "id" aSProgram.
     *
     * @param id the id of the aSProgram to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/as-programs/{id}")
    public ResponseEntity<Void> deleteASProgram(@PathVariable Long id) {
        log.debug("REST request to delete ASProgram : {}", id);

        aSProgramService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/as-programs?query=:query} : search for the aSProgram corresponding
     * to the query.
     *
     * @param query the query of the aSProgram search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/as-programs")
    public ResponseEntity<List<ASProgram>> searchASPrograms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ASPrograms for query {}", query);
        Page<ASProgram> page = aSProgramService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
