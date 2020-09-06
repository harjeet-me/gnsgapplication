package org.gnsg.gms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ExpenseReportSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ExpenseReportSearchRepositoryMockConfiguration {

    @MockBean
    private ExpenseReportSearchRepository mockExpenseReportSearchRepository;

}
