package org.gnsg.gms.repository;

import java.time.LocalDate;
import java.util.List;
import org.gnsg.gms.domain.PersistentToken;
import org.gnsg.gms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link PersistentToken} entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {
    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);
}
