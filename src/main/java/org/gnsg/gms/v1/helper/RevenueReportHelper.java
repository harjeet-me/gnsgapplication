package org.gnsg.gms.v1.helper;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.List;
import org.gnsg.gms.domain.Revenue;
import org.gnsg.gms.domain.RevenueReport;
import org.gnsg.gms.domain.enumeration.REVTYPE;
import org.gnsg.gms.repository.RevenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenueReportHelper {
    @Autowired
    RevenueRepository revenueRepository;

    private final Logger log = LoggerFactory.getLogger(RevenueReportHelper.class);

    public RevenueReport generateRevenueReport(RevenueReport revenueReport) throws RuntimeException {
        log.debug("Request to save RevenueReport : {}", revenueReport);
        List<Revenue> objectList = null;
        if (revenueReport.getRevType() != null && revenueReport.getRevType() == (REVTYPE.ALL)) {
            objectList = revenueRepository.findByDateBetween(revenueReport.getStartDate(), revenueReport.getEndDate());
        } else {
            objectList =
                revenueRepository.findByRevTypeAndDateBetween(
                    revenueReport.getRevType(),
                    revenueReport.getStartDate(),
                    revenueReport.getEndDate()
                );
        }

        String json = CsvHelper.ListJson(objectList);

        if (json == null) {
            log.warn("json object found ==null   ");
        } else {
            double sum = objectList.stream().mapToDouble(Revenue::getAmt).sum();
            byte[] generatedPdf = CsvToPdfConverter.csvToPdfConverter(
                json.getBytes(),
                new ReportObj("Revenue ", revenueReport.getStartDate(), revenueReport.getEndDate(), sum)
            );

            if (generatedPdf == null) {
                log.warn("generatedPdf==null   ");
            } else {
                revenueReport.setReport(generatedPdf);
                revenueReport.setReportContentType("application/pdf");
            }
        }

        return revenueReport;
    }
}
