package org.gnsg.gms.service;

import org.gnsg.gms.domain.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Task}.
 */
public interface TaskService {

    /**
     * Save a task.
     *
     * @param task the entity to save.
     * @return the persisted entity.
     */
    Task save(Task task);

    /**
     * Get all the tasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Task> findAll(Pageable pageable);


    /**
     * Get the "id" task.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Task> findOne(Long id);

    /**
     * Delete the "id" task.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the task corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Task> search(String query, Pageable pageable);
}
