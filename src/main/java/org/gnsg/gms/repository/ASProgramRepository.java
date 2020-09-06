package org.gnsg.gms.repository;

import org.gnsg.gms.domain.ASProgram;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ASProgram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ASProgramRepository extends JpaRepository<ASProgram, Long> {
}
