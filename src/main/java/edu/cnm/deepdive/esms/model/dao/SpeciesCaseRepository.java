package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import edu.cnm.deepdive.esms.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesCaseRepository extends JpaRepository<SpeciesCase, Long> {

  Iterable<SpeciesCase> getAllByOrderBySpeciesNameAsc();

  Optional<SpeciesCase> findByExternalKey(UUID externalKey);

  Optional<SpeciesCase> findByExternalKeyAndLeadResearcher(UUID externalKey, User lead);
}
