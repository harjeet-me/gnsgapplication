package org.gnsg.gms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link RevenueSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RevenueSearchRepositoryMockConfiguration {

    @MockBean
    private RevenueSearchRepository mockRevenueSearchRepository;

}
