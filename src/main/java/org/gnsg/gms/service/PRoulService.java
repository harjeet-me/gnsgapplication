package org.gnsg.gms.service;

import java.util.List;
import java.util.Optional;
import org.gnsg.gms.domain.PRoul;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PRoul}.
 */
public interface PRoulService {
    /**
     * Save a pRoul.
     *
     * @param pRoul the entity to save.
     * @return the persisted entity.
     */
    PRoul save(PRoul pRoul);

    /**
     * Get all the pRouls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PRoul> findAll(Pageable pageable);

    /**
     * Get the "id" pRoul.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PRoul> findOne(Long id);

    /**
     * Delete the "id" pRoul.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the pRoul corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PRoul> search(String query, Pageable pageable);

    List<PRoul> findByDesc(String desc);
}
