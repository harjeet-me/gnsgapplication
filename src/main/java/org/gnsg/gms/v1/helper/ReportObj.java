package org.gnsg.gms.v1.helper;

import java.time.LocalDate;

/**
 * @author harjeet
 *
 */
public class ReportObj {
    private String reportType;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double reportTotal;

    public ReportObj() {}

    public ReportObj(String reportType, LocalDate startDate, LocalDate endDate, Double reportTotal) {
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportTotal = reportTotal;
    }

    public Double getReportTotal() {
        return reportTotal;
    }

    public void setReportTotal(Double reportTotal) {
        this.reportTotal = reportTotal;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
