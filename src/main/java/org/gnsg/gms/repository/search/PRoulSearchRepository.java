package org.gnsg.gms.repository.search;

import org.gnsg.gms.domain.PRoul;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PRoul} entity.
 */
public interface PRoulSearchRepository extends ElasticsearchRepository<PRoul, Long> {
}
