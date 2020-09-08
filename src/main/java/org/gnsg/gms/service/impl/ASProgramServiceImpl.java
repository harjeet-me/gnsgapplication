package org.gnsg.gms.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.time.LocalDate;
import java.util.Optional;
import org.gnsg.gms.domain.ASProgram;
import org.gnsg.gms.domain.Revenue;
import org.gnsg.gms.domain.enumeration.REVTYPE;
import org.gnsg.gms.repository.ASProgramRepository;
import org.gnsg.gms.repository.RevenueRepository;
import org.gnsg.gms.repository.search.ASProgramSearchRepository;
import org.gnsg.gms.service.ASProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ASProgram}.
 */
@Service
@Transactional
public class ASProgramServiceImpl implements ASProgramService {
    private final Logger log = LoggerFactory.getLogger(ASProgramServiceImpl.class);

    private final ASProgramRepository aSProgramRepository;

    private final ASProgramSearchRepository aSProgramSearchRepository;

    @Autowired
    RevenueRepository revenueRepository;

    public ASProgramServiceImpl(ASProgramRepository aSProgramRepository, ASProgramSearchRepository aSProgramSearchRepository) {
        this.aSProgramRepository = aSProgramRepository;
        this.aSProgramSearchRepository = aSProgramSearchRepository;
    }

    /**
     * Save a aSProgram.
     *
     * @param aSProgram the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ASProgram save(ASProgram aSProgram) {
        log.debug("Request to save ASProgram : {}", aSProgram);

        Revenue revenue = new Revenue();

        revenue.setDate(LocalDate.now());
        revenue.setDesc("" + aSProgram.getProgram() + "  " + aSProgram.getFamily());
        revenue.setAmt(500.0);
        revenue.setRevType(REVTYPE.SEHAJ_PATH_BHETA);

        revenueRepository.save(revenue);
        ASProgram result = aSProgramRepository.save(aSProgram);
        aSProgramSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the aSPrograms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ASProgram> findAll(Pageable pageable) {
        log.debug("Request to get all ASPrograms");
        return aSProgramRepository.findAll(pageable);
    }

    /**
     * Get one aSProgram by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ASProgram> findOne(Long id) {
        log.debug("Request to get ASProgram : {}", id);
        return aSProgramRepository.findById(id);
    }

    /**
     * Delete the aSProgram by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ASProgram : {}", id);

        aSProgramRepository.deleteById(id);
        aSProgramSearchRepository.deleteById(id);
    }

    /**
     * Search for the aSProgram corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ASProgram> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ASPrograms for query {}", query);
        return aSProgramSearchRepository.search(queryStringQuery(query), pageable);
    }
}
