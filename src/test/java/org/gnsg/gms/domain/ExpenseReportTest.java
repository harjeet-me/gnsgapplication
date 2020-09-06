package org.gnsg.gms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.gnsg.gms.web.rest.TestUtil;

public class ExpenseReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpenseReport.class);
        ExpenseReport expenseReport1 = new ExpenseReport();
        expenseReport1.setId(1L);
        ExpenseReport expenseReport2 = new ExpenseReport();
        expenseReport2.setId(expenseReport1.getId());
        assertThat(expenseReport1).isEqualTo(expenseReport2);
        expenseReport2.setId(2L);
        assertThat(expenseReport1).isNotEqualTo(expenseReport2);
        expenseReport1.setId(null);
        assertThat(expenseReport1).isNotEqualTo(expenseReport2);
    }
}
