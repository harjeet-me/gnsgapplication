package org.gnsg.gms.service.impl;

import org.gnsg.gms.service.ExpenseReportService;
import org.gnsg.gms.domain.ExpenseReport;
import org.gnsg.gms.repository.ExpenseReportRepository;
import org.gnsg.gms.repository.search.ExpenseReportSearchRepository;
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
 * Service Implementation for managing {@link ExpenseReport}.
 */
@Service
@Transactional
public class ExpenseReportServiceImpl implements ExpenseReportService {

    private final Logger log = LoggerFactory.getLogger(ExpenseReportServiceImpl.class);

    private final ExpenseReportRepository expenseReportRepository;

    private final ExpenseReportSearchRepository expenseReportSearchRepository;

    public ExpenseReportServiceImpl(ExpenseReportRepository expenseReportRepository, ExpenseReportSearchRepository expenseReportSearchRepository) {
        this.expenseReportRepository = expenseReportRepository;
        this.expenseReportSearchRepository = expenseReportSearchRepository;
    }

    /**
     * Save a expenseReport.
     *
     * @param expenseReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ExpenseReport save(ExpenseReport expenseReport) {
        log.debug("Request to save ExpenseReport : {}", expenseReport);
        ExpenseReport result = expenseReportRepository.save(expenseReport);
        expenseReportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the expenseReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseReport> findAll() {
        log.debug("Request to get all ExpenseReports");
        return expenseReportRepository.findAll();
    }


    /**
     * Get one expenseReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExpenseReport> findOne(Long id) {
        log.debug("Request to get ExpenseReport : {}", id);
        return expenseReportRepository.findById(id);
    }

    /**
     * Delete the expenseReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExpenseReport : {}", id);

        expenseReportRepository.deleteById(id);
        expenseReportSearchRepository.deleteById(id);
    }

    /**
     * Search for the expenseReport corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseReport> search(String query) {
        log.debug("Request to search ExpenseReports for query {}", query);
        return StreamSupport
            .stream(expenseReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
