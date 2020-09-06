package org.gnsg.gms.service;

import org.gnsg.gms.domain.PathReport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PathReport}.
 */
public interface PathReportService {

    /**
     * Save a pathReport.
     *
     * @param pathReport the entity to save.
     * @return the persisted entity.
     */
    PathReport save(PathReport pathReport);

    /**
     * Get all the pathReports.
     *
     * @return the list of entities.
     */
    List<PathReport> findAll();


    /**
     * Get the "id" pathReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PathReport> findOne(Long id);

    /**
     * Delete the "id" pathReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the pathReport corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PathReport> search(String query);
}
