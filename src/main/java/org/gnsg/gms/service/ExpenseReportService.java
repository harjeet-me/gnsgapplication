package org.gnsg.gms.service;

import org.gnsg.gms.domain.ExpenseReport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ExpenseReport}.
 */
public interface ExpenseReportService {

    /**
     * Save a expenseReport.
     *
     * @param expenseReport the entity to save.
     * @return the persisted entity.
     */
    ExpenseReport save(ExpenseReport expenseReport);

    /**
     * Get all the expenseReports.
     *
     * @return the list of entities.
     */
    List<ExpenseReport> findAll();


    /**
     * Get the "id" expenseReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExpenseReport> findOne(Long id);

    /**
     * Delete the "id" expenseReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the expenseReport corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ExpenseReport> search(String query);
}
