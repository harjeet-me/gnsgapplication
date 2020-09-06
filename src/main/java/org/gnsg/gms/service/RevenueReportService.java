package org.gnsg.gms.service;

import org.gnsg.gms.domain.RevenueReport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RevenueReport}.
 */
public interface RevenueReportService {

    /**
     * Save a revenueReport.
     *
     * @param revenueReport the entity to save.
     * @return the persisted entity.
     */
    RevenueReport save(RevenueReport revenueReport);

    /**
     * Get all the revenueReports.
     *
     * @return the list of entities.
     */
    List<RevenueReport> findAll();


    /**
     * Get the "id" revenueReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RevenueReport> findOne(Long id);

    /**
     * Delete the "id" revenueReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the revenueReport corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<RevenueReport> search(String query);
}
