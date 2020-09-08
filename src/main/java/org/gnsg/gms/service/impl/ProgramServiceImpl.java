package org.gnsg.gms.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.time.LocalDate;
import java.util.Optional;
import liquibase.pro.packaged.p;
import org.gnsg.gms.domain.Program;
import org.gnsg.gms.domain.Revenue;
import org.gnsg.gms.domain.enumeration.REVTYPE;
import org.gnsg.gms.repository.ProgramRepository;
import org.gnsg.gms.repository.RevenueRepository;
import org.gnsg.gms.repository.search.ProgramSearchRepository;
import org.gnsg.gms.service.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Program}.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {
    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    private final ProgramSearchRepository programSearchRepository;

    @Autowired
    RevenueRepository revenueRepository;

    public ProgramServiceImpl(ProgramRepository programRepository, ProgramSearchRepository programSearchRepository) {
        this.programRepository = programRepository;
        this.programSearchRepository = programSearchRepository;
    }

    /**
     * Save a program.
     *
     * @param program the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Program save(Program program) {
        log.debug("Request to save Program : {}", program);
        Revenue revenue = new Revenue();

        revenue.setDate(LocalDate.now());
        revenue.setDesc("" + program.getProgramType() + "  " + program.getFamily());
        revenue.setAmt(program.getPaidAmt());
        revenue.setRevType(REVTYPE.PROGRAM);

        revenueRepository.save(revenue);

        Program result = programRepository.save(program);
        programSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the programs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Program> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable);
    }

    /**
     * Get one program by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Program> findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        return programRepository.findById(id);
    }

    /**
     * Delete the program by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);

        programRepository.deleteById(id);
        programSearchRepository.deleteById(id);
    }

    /**
     * Search for the program corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Program> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Programs for query {}", query);
        return programSearchRepository.search(queryStringQuery(query), pageable);
    }
}
