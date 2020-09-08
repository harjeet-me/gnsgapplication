package org.gnsg.gms.repository;

import java.time.LocalDate;
import java.util.List;
import org.gnsg.gms.domain.Revenue;
import org.gnsg.gms.domain.enumeration.REVTYPE;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Revenue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    List<Revenue> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Revenue> findByRevTypeAndDateBetween(REVTYPE revType, LocalDate startDate, LocalDate endDate);
}
