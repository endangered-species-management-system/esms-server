package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.Attachment;
import edu.cnm.deepdive.esms.model.entity.Evidence;
import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.util.Role;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AbstractEvidenceService {

  Evidence addEvidence(UUID speciesExternalKey, Evidence evidence, User currentUser);

  void deleteEvidence(UUID speciesExternalKey, UUID evidenceExternalKey, User currentUser);

  Iterable<Evidence> getEvidences(UUID speciesExternalKey, User currentUser);

  Evidence getEvidence(UUID speciesExternalKey, UUID evidenceExternalKey, User currentUser);
}

