package org.gnsg.gms.repository;

import java.util.List;
import org.gnsg.gms.domain.PRoul;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PRoul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PRoulRepository extends JpaRepository<PRoul, Long> {
    List<PRoul> findByPathiName(String name);
}
