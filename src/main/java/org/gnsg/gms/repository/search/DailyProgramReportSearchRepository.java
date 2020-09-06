package org.gnsg.gms.repository.search;

import org.gnsg.gms.domain.DailyProgramReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link DailyProgramReport} entity.
 */
public interface DailyProgramReportSearchRepository extends ElasticsearchRepository<DailyProgramReport, Long> {
}
