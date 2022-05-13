package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.Evidence;
import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import edu.cnm.deepdive.esms.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

  String TEAM_MEMBER_QUERY =
      "SELECT e "
          + "FROM Evidence AS e "
          + "JOIN e.speciesCase AS s "
          + "WHERE e.externalKey = :evidenceExternalKey "
          + "AND s.externalKey = :speciesExternalKey "
          + "AND (s.leadResearcher = :user OR :user MEMBER OF s.assigned)";

  Optional<Evidence> findByExternalKey(UUID externalKey);

  @Query(TEAM_MEMBER_QUERY)
  Optional<Evidence> findByExternalKeyAndTeamMember(UUID evidenceExternalKey,
      UUID speciesExternalKey, User user);

  Optional<Evidence> findByExternalKeyAndSpeciesCase_ExternalKeyAndSpeciesCase_LeadResearcher(
      UUID evidenceExternalKey, UUID speciesCaseExternalKey, User user);

  Iterable<Evidence> findAllBySpeciesCase_ExternalKeyOrderByNumberAsc(UUID externalKey);

  Optional<Evidence> findByExternalKeyAndSpeciesCase_ExternalKey(UUID evidenceExternalKey,
      UUID speciesCaseExternalKey);
}
