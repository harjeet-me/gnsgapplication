package org.gnsg.gms.repository.search;

import org.gnsg.gms.domain.Expense;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Expense} entity.
 */
public interface ExpenseSearchRepository extends ElasticsearchRepository<Expense, Long> {
}
