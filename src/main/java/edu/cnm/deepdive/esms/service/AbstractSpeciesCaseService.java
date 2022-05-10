package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import edu.cnm.deepdive.esms.model.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface AbstractSpeciesCaseService {

  /**
   * Adds an {@link SpeciesCase} to the Database.
   * @param speciesCase instance to be added
   * @param lead tied to this {@link SpeciesCase}
   * @return the added {@link SpeciesCase}
   */
  SpeciesCase addCase(SpeciesCase speciesCase, User lead);

  SpeciesCase updateCase(UUID id, SpeciesCase speciesCase, User lead);

  /**
   * Deletes this instance of {@link SpeciesCase} from the Database.
   * @param externalKey a unique identifier {@link SpeciesCase} resource.
   * @param user user tied to this instance
   */
  void deleteCase(UUID externalKey, User user);

  /**
   *  Retrieves the specified {@link SpeciesCase} from the Database.
   * @param externalKey passed to retrieve this instance from to the Database.
   * @param user tied to this instance.
   * @return the specified {@link SpeciesCase}.
   */
  Optional<SpeciesCase> getCase(UUID externalKey, User user);

  Iterable<SpeciesCase> getAllCases();
}

