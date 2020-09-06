package org.gnsg.gms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.gnsg.gms.web.rest.TestUtil;

public class ASProgramTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ASProgram.class);
        ASProgram aSProgram1 = new ASProgram();
        aSProgram1.setId(1L);
        ASProgram aSProgram2 = new ASProgram();
        aSProgram2.setId(aSProgram1.getId());
        assertThat(aSProgram1).isEqualTo(aSProgram2);
        aSProgram2.setId(2L);
        assertThat(aSProgram1).isNotEqualTo(aSProgram2);
        aSProgram1.setId(null);
        assertThat(aSProgram1).isNotEqualTo(aSProgram2);
    }
}
