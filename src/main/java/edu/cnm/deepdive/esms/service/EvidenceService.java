package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.EvidenceRepository;
import edu.cnm.deepdive.esms.model.dao.SpeciesCaseRepository;
import edu.cnm.deepdive.esms.model.dao.UserRepository;
import edu.cnm.deepdive.esms.model.entity.Evidence;
import edu.cnm.deepdive.esms.model.entity.User;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EvidenceService implements AbstractEvidenceService {

  private final UserRepository userRepository;
  private final SpeciesCaseRepository speciesCaseRepository;
  private final EvidenceRepository evidenceRepository;

  public EvidenceService(UserRepository userRepository, SpeciesCaseRepository speciesCaseRepository,
      EvidenceRepository evidenceRepository) {
    this.userRepository = userRepository;
    this.speciesCaseRepository = speciesCaseRepository;
    this.evidenceRepository = evidenceRepository;
  }

  @Override
  public Evidence addEvidence(UUID speciesExternalKey, Evidence evidence, User currentUser) {
    return speciesCaseRepository
        .findByExternalKeyAndTeamMember(speciesExternalKey, currentUser)
        .map((speciesCase) -> {
          evidence.setSpeciesCase(speciesCase);
          evidence.setUser(currentUser);
          return evidenceRepository.save(evidence);
        })
        .orElseThrow();
  }

  @Override
  public void deleteEvidence(UUID speciesExternalKey, UUID evidenceExternalKey, User currentUser) {
    evidenceRepository
        .findByExternalKeyAndSpeciesCase_ExternalKeyAndSpeciesCase_LeadResearcher(
            evidenceExternalKey, speciesExternalKey, currentUser)
        .ifPresent(evidenceRepository::delete);

  }

  @Override
  public Iterable<Evidence> getEvidences(UUID speciesExternalKey, User currentUser) {
    return evidenceRepository
        .findAllBySpeciesCase_ExternalKeyOrderByNumberAsc(speciesExternalKey);
  }

  @Override
  public Evidence getEvidence(UUID speciesExternalKey, UUID evidenceExternalKey, User currentUser) {
    return evidenceRepository
        .findByExternalKeyAndSpeciesCase_ExternalKey(evidenceExternalKey, speciesExternalKey)
        .orElseThrow();
  }
}
