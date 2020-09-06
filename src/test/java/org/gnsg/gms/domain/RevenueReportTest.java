package org.gnsg.gms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.gnsg.gms.web.rest.TestUtil;

public class RevenueReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RevenueReport.class);
        RevenueReport revenueReport1 = new RevenueReport();
        revenueReport1.setId(1L);
        RevenueReport revenueReport2 = new RevenueReport();
        revenueReport2.setId(revenueReport1.getId());
        assertThat(revenueReport1).isEqualTo(revenueReport2);
        revenueReport2.setId(2L);
        assertThat(revenueReport1).isNotEqualTo(revenueReport2);
        revenueReport1.setId(null);
        assertThat(revenueReport1).isNotEqualTo(revenueReport2);
    }
}
