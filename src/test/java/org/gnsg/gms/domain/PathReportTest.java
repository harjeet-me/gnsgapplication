package org.gnsg.gms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.gnsg.gms.web.rest.TestUtil;

public class PathReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PathReport.class);
        PathReport pathReport1 = new PathReport();
        pathReport1.setId(1L);
        PathReport pathReport2 = new PathReport();
        pathReport2.setId(pathReport1.getId());
        assertThat(pathReport1).isEqualTo(pathReport2);
        pathReport2.setId(2L);
        assertThat(pathReport1).isNotEqualTo(pathReport2);
        pathReport1.setId(null);
        assertThat(pathReport1).isNotEqualTo(pathReport2);
    }
}
