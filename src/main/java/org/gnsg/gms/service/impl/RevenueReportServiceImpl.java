package org.gnsg.gms.service.impl;

import org.gnsg.gms.service.RevenueReportService;
import org.gnsg.gms.domain.RevenueReport;
import org.gnsg.gms.repository.RevenueReportRepository;
import org.gnsg.gms.repository.search.RevenueReportSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RevenueReport}.
 */
@Service
@Transactional
public class RevenueReportServiceImpl implements RevenueReportService {

    private final Logger log = LoggerFactory.getLogger(RevenueReportServiceImpl.class);

    private final RevenueReportRepository revenueReportRepository;

    private final RevenueReportSearchRepository revenueReportSearchRepository;

    public RevenueReportServiceImpl(RevenueReportRepository revenueReportRepository, RevenueReportSearchRepository revenueReportSearchRepository) {
        this.revenueReportRepository = revenueReportRepository;
        this.revenueReportSearchRepository = revenueReportSearchRepository;
    }

    /**
     * Save a revenueReport.
     *
     * @param revenueReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RevenueReport save(RevenueReport revenueReport) {
        log.debug("Request to save RevenueReport : {}", revenueReport);
        RevenueReport result = revenueReportRepository.save(revenueReport);
        revenueReportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the revenueReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RevenueReport> findAll() {
        log.debug("Request to get all RevenueReports");
        return revenueReportRepository.findAll();
    }


    /**
     * Get one revenueReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RevenueReport> findOne(Long id) {
        log.debug("Request to get RevenueReport : {}", id);
        return revenueReportRepository.findById(id);
    }

    /**
     * Delete the revenueReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RevenueReport : {}", id);

        revenueReportRepository.deleteById(id);
        revenueReportSearchRepository.deleteById(id);
    }

    /**
     * Search for the revenueReport corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RevenueReport> search(String query) {
        log.debug("Request to search RevenueReports for query {}", query);
        return StreamSupport
            .stream(revenueReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
