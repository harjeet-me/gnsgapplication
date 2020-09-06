package org.gnsg.gms.repository.search;

import org.gnsg.gms.domain.Vendor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Vendor} entity.
 */
public interface VendorSearchRepository extends ElasticsearchRepository<Vendor, Long> {
}
