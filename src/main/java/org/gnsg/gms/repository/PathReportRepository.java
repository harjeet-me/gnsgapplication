package org.gnsg.gms.repository;

import org.gnsg.gms.domain.PathReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PathReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathReportRepository extends JpaRepository<PathReport, Long> {
}
