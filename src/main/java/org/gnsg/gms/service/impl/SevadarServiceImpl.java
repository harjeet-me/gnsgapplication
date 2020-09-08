package org.gnsg.gms.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;
import org.gnsg.gms.domain.Sevadar;
import org.gnsg.gms.repository.SevadarRepository;
import org.gnsg.gms.repository.search.SevadarSearchRepository;
import org.gnsg.gms.service.SevadarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sevadar}.
 */
@Service
@Transactional
public class SevadarServiceImpl implements SevadarService {
    private final Logger log = LoggerFactory.getLogger(SevadarServiceImpl.class);

    private final SevadarRepository sevadarRepository;

    private final SevadarSearchRepository sevadarSearchRepository;

    public SevadarServiceImpl(SevadarRepository sevadarRepository, SevadarSearchRepository sevadarSearchRepository) {
        this.sevadarRepository = sevadarRepository;
        this.sevadarSearchRepository = sevadarSearchRepository;
    }

    @Override
    public Sevadar save(Sevadar sevadar) {
        log.debug("Request to save Sevadar : {}", sevadar);
        Sevadar result = sevadarRepository.save(sevadar);
        sevadarSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sevadar> findAll(Pageable pageable) {
        log.debug("Request to get all Sevadars");
        return sevadarRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sevadar> findOne(Long id) {
        log.debug("Request to get Sevadar : {}", id);
        return sevadarRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sevadar : {}", id);
        sevadarRepository.deleteById(id);
        sevadarSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sevadar> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sevadars for query {}", query);
        return sevadarSearchRepository.search(queryStringQuery(query), pageable);
    }
}
