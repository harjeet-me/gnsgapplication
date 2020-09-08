package org.gnsg.gms.v1.helper;

import java.util.List;
import org.gnsg.gms.domain.Expense;
import org.gnsg.gms.domain.ExpenseReport;
import org.gnsg.gms.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseReportHelper {
    @Autowired
    ExpenseRepository expenseRepository;

    private final Logger log = LoggerFactory.getLogger(ExpenseReportHelper.class);

    public ExpenseReport generateExpenseReport(ExpenseReport expenseReport) {
        List<Expense> expenses = expenseRepository.findByDateBetween(expenseReport.getStartDate(), expenseReport.getEndDate());
        log.warn("json object found   " + expenses);
        String json = CsvHelper.ListJson(expenses);

        if (json == null) {
            log.warn("json object found ==null   ");
        } else {
            double sum = expenses.stream().mapToDouble(Expense::getAmt).sum();
            byte[] generatedPdf = CsvToPdfConverter.csvToPdfConverter(
                json.getBytes(),
                new ReportObj("Expense ", expenseReport.getStartDate(), expenseReport.getEndDate(), sum)
            );

            if (generatedPdf == null) {
                log.warn("generatedPdf==null   ");
            } else {
                expenseReport.setReport(generatedPdf);
                expenseReport.setReportContentType("application/pdf");
            }
        }

        return expenseReport;
    }
}
