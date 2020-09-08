package org.gnsg.gms.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.gnsg.gms.domain.Expense;
import org.gnsg.gms.domain.ExpenseReport;
import org.gnsg.gms.repository.ExpenseReportRepository;
import org.gnsg.gms.repository.ExpenseRepository;
import org.gnsg.gms.repository.search.ExpenseReportSearchRepository;
import org.gnsg.gms.service.ExpenseReportService;
import org.gnsg.gms.v1.helper.CsvHelper;
import org.gnsg.gms.v1.helper.CsvToPdfConverter;
import org.gnsg.gms.v1.helper.ExpenseReportHelper;
import org.gnsg.gms.v1.helper.ReportObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExpenseReport}.
 */
@Service
@Transactional
public class ExpenseReportServiceImpl implements ExpenseReportService {
    private final Logger log = LoggerFactory.getLogger(ExpenseReportServiceImpl.class);

    private final ExpenseReportRepository expenseReportRepository;

    @Autowired
    ExpenseReportHelper expenseReportHelper;

    private final ExpenseReportSearchRepository expenseReportSearchRepository;

    public ExpenseReportServiceImpl(
        ExpenseReportRepository expenseReportRepository,
        ExpenseReportSearchRepository expenseReportSearchRepository
    ) {
        this.expenseReportRepository = expenseReportRepository;
        this.expenseReportSearchRepository = expenseReportSearchRepository;
    }

    @Override
    public ExpenseReport save(ExpenseReport expenseReport) {
        log.debug("Request to save ExpenseReport : {}", expenseReport);
        expenseReport = expenseReportHelper.generateExpenseReport(expenseReport);
        ExpenseReport result = expenseReportRepository.save(expenseReport);
        expenseReportSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseReport> findAll() {
        log.debug("Request to get all ExpenseReports");
        return expenseReportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExpenseReport> findOne(Long id) {
        log.debug("Request to get ExpenseReport : {}", id);
        return expenseReportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExpenseReport : {}", id);
        expenseReportRepository.deleteById(id);
        expenseReportSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseReport> search(String query) {
        log.debug("Request to search ExpenseReports for query {}", query);
        return StreamSupport
            .stream(expenseReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
