package org.gnsg.gms.service;

import org.gnsg.gms.domain.ASProgram;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ASProgram}.
 */
public interface ASProgramService {

    /**
     * Save a aSProgram.
     *
     * @param aSProgram the entity to save.
     * @return the persisted entity.
     */
    ASProgram save(ASProgram aSProgram);

    /**
     * Get all the aSPrograms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ASProgram> findAll(Pageable pageable);


    /**
     * Get the "id" aSProgram.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ASProgram> findOne(Long id);

    /**
     * Delete the "id" aSProgram.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the aSProgram corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ASProgram> search(String query, Pageable pageable);
}
