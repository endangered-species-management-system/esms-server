package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.SpeciesCaseRepository;
import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class SpeciesCaseService {

  private final SpeciesCaseRepository caseRepository;

  public SpeciesCaseService(SpeciesCaseRepository caseRepository) {
    this.caseRepository = caseRepository;
  }

  public SpeciesCase newCase(SpeciesCase speciesCase) {
    return caseRepository.save(speciesCase);
  }


  public SpeciesCase get(UUID externalKey) {
    return caseRepository
        .findByExternalKey(externalKey)
        .orElseThrow();
  }
}
