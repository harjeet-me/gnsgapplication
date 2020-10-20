package org.gnsg.gms.repository;

import java.time.LocalDate;
import java.util.List;
import org.aspectj.weaver.tools.Trace;
import org.gnsg.gms.domain.Expense;
import org.gnsg.gms.domain.PRoul;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PRoul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PRoulRepository extends JpaRepository<PRoul, Long> {
    List<PRoul> findByPathiNameAndBhogDateBetween(String name, LocalDate startDate, LocalDate endDate);
    List<PRoul> findByPathiNameAndBhogDateBetweenAndDescStartsWith(String name, LocalDate startDate, LocalDate endDate, String desc);
    List<PRoul> findByBhogDateBetween(LocalDate startDate, LocalDate endDate);
    List<PRoul> findByDesc(String desc);
    List<PRoul> findByPathiNameAndDesc(String name, String string);
    List<PRoul> findByPathiNameAndDescStartsWith(String name, String string);

    @Query(
        value = "SELECT DISTINCT ( pro.pathi_id )  ,    pro.pathi_Name,   SUM (pro.total_Roul)  as  RoulTotoal   FROM  P_Roul pro where pro.bhog_date BETWEEN :STARTDATE AND :ENDDATE GROUP BY pro.pathi_id ",
        nativeQuery = true
    )
    List<PRoul> findAllPaybelDutiesByBhogDateBetween(@Param("STARTDATE") LocalDate fromDate, @Param("ENDDATE") LocalDate ENDDATE);
}
