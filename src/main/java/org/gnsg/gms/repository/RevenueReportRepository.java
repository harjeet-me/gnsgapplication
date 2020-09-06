package org.gnsg.gms.repository;

import org.gnsg.gms.domain.RevenueReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RevenueReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevenueReportRepository extends JpaRepository<RevenueReport, Long> {
}
