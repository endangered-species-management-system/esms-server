package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.SpeciesCaseRepository;
import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import edu.cnm.deepdive.esms.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class SpeciesCaseService implements AbstractSpeciesCaseService {

  private final SpeciesCaseRepository caseRepository;

  public SpeciesCaseService(SpeciesCaseRepository caseRepository) {
    this.caseRepository = caseRepository;
  }

  public SpeciesCase get(UUID externalKey) {
    return caseRepository
        .findByExternalKey(externalKey)
        .orElseThrow();
  }

  @Override
  public SpeciesCase addCase(SpeciesCase speciesCase, User lead) {
    speciesCase.setLeadResearcher(lead);
    return caseRepository.save(speciesCase);
  }

  @Override
  public void deleteCase(UUID externalKey, User user) {

  }

  @Override
  public Optional<SpeciesCase> getCase(UUID externalKey, User user) {
    return Optional.empty();
  }
}
