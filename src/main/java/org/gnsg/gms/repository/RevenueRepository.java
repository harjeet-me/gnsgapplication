package org.gnsg.gms.repository;

import java.time.LocalDate;
import java.util.List;
import org.gnsg.gms.domain.Revenue;
import org.gnsg.gms.domain.enumeration.REVTYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Revenue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    public List<Revenue> findByRevTypeAndDateBetween(REVTYPE revtype, LocalDate dateStart, LocalDate dateEnd);

    public List<Revenue> findByDateBetween(LocalDate dateStart, LocalDate dateEnd);
}
