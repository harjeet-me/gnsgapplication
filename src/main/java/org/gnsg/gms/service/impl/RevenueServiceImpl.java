package org.gnsg.gms.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.gnsg.gms.domain.Revenue;
import org.gnsg.gms.repository.RevenueRepository;
import org.gnsg.gms.repository.search.RevenueSearchRepository;
import org.gnsg.gms.service.RevenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Revenue}.
 */
@Service
@Transactional
public class RevenueServiceImpl implements RevenueService {
    private final Logger log = LoggerFactory.getLogger(RevenueServiceImpl.class);

    private final RevenueRepository revenueRepository;

    private final RevenueSearchRepository revenueSearchRepository;

    public RevenueServiceImpl(RevenueRepository revenueRepository, RevenueSearchRepository revenueSearchRepository) {
        this.revenueRepository = revenueRepository;
        this.revenueSearchRepository = revenueSearchRepository;
    }

    @Override
    public Revenue save(Revenue revenue) {
        log.debug("Request to save Revenue : {}", revenue);
        Revenue result = revenueRepository.save(revenue);
        revenueSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revenue> findAll() {
        log.debug("Request to get all Revenues");
        return revenueRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Revenue> findOne(Long id) {
        log.debug("Request to get Revenue : {}", id);
        return revenueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Revenue : {}", id);
        revenueRepository.deleteById(id);
        revenueSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revenue> search(String query) {
        log.debug("Request to search Revenues for query {}", query);
        return StreamSupport
            .stream(revenueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
