package org.gnsg.gms.repository;

import java.time.LocalDate;
import java.util.List;
import org.gnsg.gms.domain.Expense;
import org.gnsg.gms.domain.PRoul;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PRoul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PRoulRepository extends JpaRepository<PRoul, Long> {
    List<PRoul> findByPathiNameAndBhogDateBetween(String name, LocalDate startDate, LocalDate endDate);
    List<PRoul> findByPathiNameAndBhogDateBetweenAndDescStartsWith(String name, LocalDate startDate, LocalDate endDate, String desc);

    List<PRoul> findByBhogDateBetween(LocalDate startDate, LocalDate endDate);
}
