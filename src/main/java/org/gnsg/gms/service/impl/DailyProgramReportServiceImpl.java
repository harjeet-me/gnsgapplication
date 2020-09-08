package org.gnsg.gms.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.gnsg.gms.domain.DailyProgramReport;
import org.gnsg.gms.repository.DailyProgramReportRepository;
import org.gnsg.gms.repository.search.DailyProgramReportSearchRepository;
import org.gnsg.gms.service.DailyProgramReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DailyProgramReport}.
 */
@Service
@Transactional
public class DailyProgramReportServiceImpl implements DailyProgramReportService {
    private final Logger log = LoggerFactory.getLogger(DailyProgramReportServiceImpl.class);

    private final DailyProgramReportRepository dailyProgramReportRepository;

    private final DailyProgramReportSearchRepository dailyProgramReportSearchRepository;

    public DailyProgramReportServiceImpl(
        DailyProgramReportRepository dailyProgramReportRepository,
        DailyProgramReportSearchRepository dailyProgramReportSearchRepository
    ) {
        this.dailyProgramReportRepository = dailyProgramReportRepository;
        this.dailyProgramReportSearchRepository = dailyProgramReportSearchRepository;
    }

    @Override
    public DailyProgramReport save(DailyProgramReport dailyProgramReport) {
        log.debug("Request to save DailyProgramReport : {}", dailyProgramReport);
        DailyProgramReport result = dailyProgramReportRepository.save(dailyProgramReport);
        dailyProgramReportSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyProgramReport> findAll() {
        log.debug("Request to get all DailyProgramReports");
        return dailyProgramReportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DailyProgramReport> findOne(Long id) {
        log.debug("Request to get DailyProgramReport : {}", id);
        return dailyProgramReportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DailyProgramReport : {}", id);
        dailyProgramReportRepository.deleteById(id);
        dailyProgramReportSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyProgramReport> search(String query) {
        log.debug("Request to search DailyProgramReports for query {}", query);
        return StreamSupport
            .stream(dailyProgramReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
