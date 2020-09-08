package org.gnsg.gms.v1.helper;

import java.util.List;
import org.gnsg.gms.domain.Expense;
import org.gnsg.gms.domain.ExpenseReport;
import org.gnsg.gms.domain.PRoul;
import org.gnsg.gms.domain.PathReport;
import org.gnsg.gms.repository.ExpenseRepository;
import org.gnsg.gms.repository.PRoulRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathiReportHelper {
    @Autowired
    PRoulRepository pRoulRepository;

    private final Logger log = LoggerFactory.getLogger(PathiReportHelper.class);

    public PathReport generatePathReport(PathReport pathReport) {
        List<PRoul> rouls = pRoulRepository.findByPathiName("Harjeet");
        log.warn("json object found   " + rouls);
        String json = CsvHelper.ListJson(rouls);

        if (json == null) {
            log.warn("json object found ==null   ");
        } else {
            // double sum = rouls.stream().mapToDouble(PRoul::getTotalRoul).sum();
            byte[] generatedPdf = CsvToPdfConverter.csvToPdfConverter(
                json.getBytes(),
                new ReportObj("Expense ", pathReport.getStartDate(), pathReport.getEndDate(), 75575.33)
            );

            if (generatedPdf == null) {
                log.warn("generatedPdf==null   ");
            } else {
                pathReport.setReport(generatedPdf);
                pathReport.setReportContentType("application/pdf");
            }
        }

        return pathReport;
    }
}
