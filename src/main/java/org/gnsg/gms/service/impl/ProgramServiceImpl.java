package org.gnsg.gms.service.impl;

import org.gnsg.gms.service.ProgramService;
import org.gnsg.gms.domain.Program;
import org.gnsg.gms.repository.ProgramRepository;
import org.gnsg.gms.repository.search.ProgramSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Program}.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    private final ProgramSearchRepository programSearchRepository;

    public ProgramServiceImpl(ProgramRepository programRepository, ProgramSearchRepository programSearchRepository) {
        this.programRepository = programRepository;
        this.programSearchRepository = programSearchRepository;
    }

    @Override
    public Program save(Program program) {
        log.debug("Request to save Program : {}", program);
        Program result = programRepository.save(program);
        programSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Program> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Program> findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        return programRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.deleteById(id);
        programSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Program> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Programs for query {}", query);
        return programSearchRepository.search(queryStringQuery(query), pageable);    }
}
