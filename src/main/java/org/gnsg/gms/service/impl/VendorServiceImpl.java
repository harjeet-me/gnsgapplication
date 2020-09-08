package org.gnsg.gms.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.gnsg.gms.domain.Vendor;
import org.gnsg.gms.repository.VendorRepository;
import org.gnsg.gms.repository.search.VendorSearchRepository;
import org.gnsg.gms.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vendor}.
 */
@Service
@Transactional
public class VendorServiceImpl implements VendorService {
    private final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    private final VendorRepository vendorRepository;

    private final VendorSearchRepository vendorSearchRepository;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorSearchRepository vendorSearchRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorSearchRepository = vendorSearchRepository;
    }

    @Override
    public Vendor save(Vendor vendor) {
        log.debug("Request to save Vendor : {}", vendor);
        Vendor result = vendorRepository.save(vendor);
        vendorSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vendor> findAll() {
        log.debug("Request to get all Vendors");
        return vendorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vendor> findOne(Long id) {
        log.debug("Request to get Vendor : {}", id);
        return vendorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vendor : {}", id);
        vendorRepository.deleteById(id);
        vendorSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vendor> search(String query) {
        log.debug("Request to search Vendors for query {}", query);
        return StreamSupport
            .stream(vendorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
