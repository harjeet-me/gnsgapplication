package org.gnsg.gms.repository;

import java.time.LocalDate;
import java.util.List;
import org.gnsg.gms.domain.Expense;
import org.gnsg.gms.domain.enumeration.EXPTYPE;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Expense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Expense> findByExpTypeAndDateBetween(EXPTYPE expType, LocalDate startDate, LocalDate endDate);
}
