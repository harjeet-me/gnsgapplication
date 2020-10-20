package org.gnsg.gms.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.gnsg.gms.domain.RevenueReport;
import org.gnsg.gms.repository.RevenueReportRepository;
import org.gnsg.gms.repository.search.RevenueReportSearchRepository;
import org.gnsg.gms.service.RevenueReportService;
import org.gnsg.gms.v1.helper.RevenueReportHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RevenueReport}.
 */
@Service
@Transactional
public class RevenueReportServiceImpl implements RevenueReportService {
    private final Logger log = LoggerFactory.getLogger(RevenueReportServiceImpl.class);

    private final RevenueReportRepository revenueReportRepository;

    private final RevenueReportSearchRepository revenueReportSearchRepository;

    @Autowired
    RevenueReportHelper revenueReportHelper;

    public RevenueReportServiceImpl(
        RevenueReportRepository revenueReportRepository,
        RevenueReportSearchRepository revenueReportSearchRepository
    ) {
        this.revenueReportRepository = revenueReportRepository;
        this.revenueReportSearchRepository = revenueReportSearchRepository;
    }

    @Override
    public RevenueReport save(RevenueReport revenueReport) {
        log.debug("Request to save RevenueReport : {}", revenueReport);
        revenueReport = revenueReportHelper.generateRevenueReport(revenueReport);
        RevenueReport result = revenueReportRepository.save(revenueReport);
        revenueReportSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevenueReport> findAll() {
        log.debug("Request to get all RevenueReports");
        return revenueReportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RevenueReport> findOne(Long id) {
        log.debug("Request to get RevenueReport : {}", id);
        return revenueReportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RevenueReport : {}", id);
        revenueReportRepository.deleteById(id);
        revenueReportSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevenueReport> search(String query) {
        log.debug("Request to search RevenueReports for query {}", query);
        return StreamSupport
            .stream(revenueReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
