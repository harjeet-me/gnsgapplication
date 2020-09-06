package org.gnsg.gms.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DailyProgramReportSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DailyProgramReportSearchRepositoryMockConfiguration {

    @MockBean
    private DailyProgramReportSearchRepository mockDailyProgramReportSearchRepository;

}
