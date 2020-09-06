package org.gnsg.gms.repository;

import org.gnsg.gms.domain.DailyProgramReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DailyProgramReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyProgramReportRepository extends JpaRepository<DailyProgramReport, Long> {
}
