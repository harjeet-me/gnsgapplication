package org.gnsg.gms.service;

import org.gnsg.gms.domain.DailyProgramReport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DailyProgramReport}.
 */
public interface DailyProgramReportService {

    /**
     * Save a dailyProgramReport.
     *
     * @param dailyProgramReport the entity to save.
     * @return the persisted entity.
     */
    DailyProgramReport save(DailyProgramReport dailyProgramReport);

    /**
     * Get all the dailyProgramReports.
     *
     * @return the list of entities.
     */
    List<DailyProgramReport> findAll();


    /**
     * Get the "id" dailyProgramReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DailyProgramReport> findOne(Long id);

    /**
     * Delete the "id" dailyProgramReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the dailyProgramReport corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<DailyProgramReport> search(String query);
}
