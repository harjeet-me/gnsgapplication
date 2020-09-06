package org.gnsg.gms.repository;

import org.gnsg.gms.domain.ExpenseReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExpenseReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpenseReportRepository extends JpaRepository<ExpenseReport, Long> {
}
