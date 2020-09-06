package org.gnsg.gms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.gnsg.gms.web.rest.TestUtil;

public class DailyProgramReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyProgramReport.class);
        DailyProgramReport dailyProgramReport1 = new DailyProgramReport();
        dailyProgramReport1.setId(1L);
        DailyProgramReport dailyProgramReport2 = new DailyProgramReport();
        dailyProgramReport2.setId(dailyProgramReport1.getId());
        assertThat(dailyProgramReport1).isEqualTo(dailyProgramReport2);
        dailyProgramReport2.setId(2L);
        assertThat(dailyProgramReport1).isNotEqualTo(dailyProgramReport2);
        dailyProgramReport1.setId(null);
        assertThat(dailyProgramReport1).isNotEqualTo(dailyProgramReport2);
    }
}
