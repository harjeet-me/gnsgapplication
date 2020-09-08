package org.gnsg.gms.repository;

import java.util.List;
import java.util.Optional;
import org.gnsg.gms.domain.ASProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ASProgram entity.
 */
@Repository
public interface ASProgramRepository extends JpaRepository<ASProgram, Long> {
    @Query(
        value = "select distinct aSProgram from ASProgram aSProgram left join fetch aSProgram.granthis",
        countQuery = "select count(distinct aSProgram) from ASProgram aSProgram"
    )
    Page<ASProgram> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct aSProgram from ASProgram aSProgram left join fetch aSProgram.granthis")
    List<ASProgram> findAllWithEagerRelationships();

    @Query("select aSProgram from ASProgram aSProgram left join fetch aSProgram.granthis where aSProgram.id =:id")
    Optional<ASProgram> findOneWithEagerRelationships(@Param("id") Long id);
}
