package org.gnsg.gms.v1.helper;

import java.util.List;
import org.gnsg.gms.domain.Expense;
import org.gnsg.gms.domain.ExpenseReport;
import org.gnsg.gms.domain.PRoul;
import org.gnsg.gms.domain.PathReport;
import org.gnsg.gms.domain.enumeration.PATHSEARCHBY;
import org.gnsg.gms.domain.enumeration.PROGTYPE;
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

    public static final int PathiBheta = 20;

    private final Logger log = LoggerFactory.getLogger(PathiReportHelper.class);

    public PathReport generatePathReport(PathReport pathReport) {
        List<PRoul> rouls = null;
        if (pathReport.getSearchBy().equals(PATHSEARCHBY.PATHI_SINGH_NAME)) {
            if (pathReport.getPathi() != null && pathReport.getPathi().getName() != null && pathReport.getPathType().equals(PROGTYPE.ALL)) {
                rouls =
                    pRoulRepository.findByPathiNameAndBhogDateBetween(
                        pathReport.getPathi().getName(),
                        pathReport.getStartDate(),
                        pathReport.getEndDate()
                    );
            } else if (pathReport.getPathType() != PROGTYPE.ALL) {
                rouls =
                    pRoulRepository.findByPathiNameAndBhogDateBetweenAndDescStartsWith(
                        pathReport.getPathi().getName(),
                        pathReport.getStartDate(),
                        pathReport.getEndDate(),
                        pathReport.getPathType().toString()
                    );
            } else {
                log.warn("  Report type is selected pathi singh name but pathi not selected");
            }
        } else {
            rouls = pRoulRepository.findByBhogDateBetween(pathReport.getStartDate(), pathReport.getEndDate());
        }

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
