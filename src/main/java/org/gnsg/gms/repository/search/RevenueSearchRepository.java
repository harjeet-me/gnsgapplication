package org.gnsg.gms.repository.search;

import org.gnsg.gms.domain.Revenue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Revenue} entity.
 */
public interface RevenueSearchRepository extends ElasticsearchRepository<Revenue, Long> {
}
