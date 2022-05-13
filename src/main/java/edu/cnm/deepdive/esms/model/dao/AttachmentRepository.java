package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.Attachment;
import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import edu.cnm.deepdive.esms.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

  Iterable<Attachment> findAllByUserOrderByCreated(User user);

  Iterable<Attachment> findAllByEvidence_SpeciesCase_ExternalKeyAndEvidence_ExternalKeyOrderByName(
      UUID speciesCaseExternalKey, UUID evidenceExternalKey);

  Optional<Attachment> findByEvidence_SpeciesCase_ExternalKeyAndEvidence_ExternalKeyAndExternalKeyOrderByName(
      UUID speciesCaseExternalKey, UUID evidenceExternalKey, UUID attachmentExternalKey);

  Optional<Attachment> findByEvidence_SpeciesCase_ExternalKeyAndEvidence_ExternalKeyAndExternalKeyAndEvidence_SpeciesCase_LeadResearcherOrderByName(
      UUID speciesCaseExternalKey, UUID evidenceExternalKey, UUID attachmentExternalKey, User user);

}
