package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.SpeciesCaseRepository;
import edu.cnm.deepdive.esms.model.dao.UserRepository;
import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.util.Role;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SpeciesCaseService implements AbstractSpeciesCaseService {

  private final SpeciesCaseRepository caseRepository;
  private final UserRepository userRepository;

  public SpeciesCaseService(SpeciesCaseRepository caseRepository, UserRepository userRepository) {
    this.caseRepository = caseRepository;
    this.userRepository = userRepository;
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
  public SpeciesCase updateCase(UUID id, SpeciesCase speciesCase, User lead) {
    return caseRepository
        .findByExternalKeyAndLeadResearcher(id, lead)
        .map((species) -> {
          species.setPhase(speciesCase.getPhase());
          species.setSummary(speciesCase.getSummary());
          species.setDetailedDescription(speciesCase.getDetailedDescription());
          return caseRepository.save(species);
        })
        .orElseThrow();
  }

  @Override
  public void deleteCase(UUID externalKey, User user) {

  }

  @Override
  public Iterable<SpeciesCase> getAllCases() {
    return caseRepository.getAllByOrderBySpeciesNameAsc();
  }

  @Transactional
  @Override
  public Iterable<User> getTeam(UUID externalKey) {
    return caseRepository
        .findByExternalKey(externalKey)
        .map(SpeciesCase::getAssigned)
        .orElseThrow();
  }

  @Override
  public boolean setTeamMember(UUID speciesCaseExternalKey, UUID userExternalKey, boolean inTeam,
      User currentUser) {
    Optional<User> userQuery = inTeam
        ? userRepository.findByExternalKeyAndRolesContainsAndInactive(userExternalKey,
        Role.RESEARCHER, false)
        : userRepository.findByExternalKey(userExternalKey);
    return userQuery
        .flatMap((user) -> caseRepository
            .findByExternalKeyAndLeadResearcher(speciesCaseExternalKey, currentUser)
            .map((speciesCase) -> {
              List<SpeciesCase> cases = user.getCases();
              Set<User> assigned = speciesCase.getAssigned();
              if (inTeam && !assigned.contains(user)) {
                cases.add(speciesCase);
              } else if (!inTeam && assigned.contains(user)) {
                cases.remove(speciesCase);
              }
              userRepository.save(user);
              return inTeam;
            }))
        .orElseThrow();
  }
}
