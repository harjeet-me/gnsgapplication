package org.gnsg.gms.repository.search;

import org.gnsg.gms.domain.ExpenseReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ExpenseReport} entity.
 */
public interface ExpenseReportSearchRepository extends ElasticsearchRepository<ExpenseReport, Long> {
}
