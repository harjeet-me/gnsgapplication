package org.gnsg.gms.service.impl;

import org.gnsg.gms.service.PathReportService;
import org.gnsg.gms.domain.PathReport;
import org.gnsg.gms.repository.PathReportRepository;
import org.gnsg.gms.repository.search.PathReportSearchRepository;
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
 * Service Implementation for managing {@link PathReport}.
 */
@Service
@Transactional
public class PathReportServiceImpl implements PathReportService {

    private final Logger log = LoggerFactory.getLogger(PathReportServiceImpl.class);

    private final PathReportRepository pathReportRepository;

    private final PathReportSearchRepository pathReportSearchRepository;

    public PathReportServiceImpl(PathReportRepository pathReportRepository, PathReportSearchRepository pathReportSearchRepository) {
        this.pathReportRepository = pathReportRepository;
        this.pathReportSearchRepository = pathReportSearchRepository;
    }

    @Override
    public PathReport save(PathReport pathReport) {
        log.debug("Request to save PathReport : {}", pathReport);
        PathReport result = pathReportRepository.save(pathReport);
        pathReportSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PathReport> findAll() {
        log.debug("Request to get all PathReports");
        return pathReportRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PathReport> findOne(Long id) {
        log.debug("Request to get PathReport : {}", id);
        return pathReportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PathReport : {}", id);
        pathReportRepository.deleteById(id);
        pathReportSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PathReport> search(String query) {
        log.debug("Request to search PathReports for query {}", query);
        return StreamSupport
            .stream(pathReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
