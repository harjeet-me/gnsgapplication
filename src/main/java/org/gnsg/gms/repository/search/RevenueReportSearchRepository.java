package org.gnsg.gms.repository.search;

import org.gnsg.gms.domain.RevenueReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link RevenueReport} entity.
 */
public interface RevenueReportSearchRepository extends ElasticsearchRepository<RevenueReport, Long> {
}
