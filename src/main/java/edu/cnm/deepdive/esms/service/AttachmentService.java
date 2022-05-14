package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.AttachmentRepository;
import edu.cnm.deepdive.esms.model.dao.EvidenceRepository;
import edu.cnm.deepdive.esms.model.dao.UserRepository;
import edu.cnm.deepdive.esms.model.entity.Attachment;
import edu.cnm.deepdive.esms.model.entity.User;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentService implements AbstractAttachmentService {

  private static final String UNTITLED_FILENAME = "untitled";

  private final AttachmentRepository attachmentRepository;
  private final EvidenceRepository evidenceRepository;
  private final StorageService storageService;

  public AttachmentService(AttachmentRepository attachmentRepository, EvidenceRepository evidenceRepository,
      StorageService storageService) {
    this.attachmentRepository = attachmentRepository;
    this.evidenceRepository = evidenceRepository;
    this.storageService = storageService;
  }

  @Override
  public Optional<Attachment> get(@NonNull UUID speciesCaseExternalKey, UUID evidenceExternalKey,
      UUID attachmentExternalKey) {
    return attachmentRepository
        .findByEvidence_SpeciesCase_ExternalKeyAndEvidence_ExternalKeyAndExternalKeyOrderByName(
            speciesCaseExternalKey, evidenceExternalKey, attachmentExternalKey);
  }

  @Override
  public Attachment getAttachment(UUID speciesExternalKey, UUID evidenceExternalKey,
      UUID attachmentExternalKey, User currentUser) {
    return null;
  }

  @Override
  public Iterable<Attachment> list(@NonNull UUID speciesCaseExternalKey,
      @NonNull UUID evidenceExternalKey) {
    return attachmentRepository
        .findAllByEvidence_SpeciesCase_ExternalKeyAndEvidence_ExternalKeyOrderByName(
            speciesCaseExternalKey, evidenceExternalKey);
  }

  @Override
  public void delete(@NonNull UUID speciesCaseExternalKey, UUID evidenceExternalKey,
      UUID attachmentExternalKey, User currentUser) throws IOException {
    attachmentRepository
        .findByEvidence_SpeciesCase_ExternalKeyAndEvidence_ExternalKeyAndExternalKeyAndEvidence_SpeciesCase_LeadResearcherOrderByName(
            speciesCaseExternalKey, evidenceExternalKey, attachmentExternalKey, currentUser)
        .ifPresent((attachment) -> {
          try {
            storageService.delete(attachment.getStorageKey());
            attachmentRepository.delete(attachment);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public Attachment save(Attachment attachment, @NonNull UUID speciesCaseExternalKey,
      UUID evidenceExternalKey, User currentUser) {
    return evidenceRepository
        .findByExternalKeyAndTeamMember(evidenceExternalKey, speciesCaseExternalKey, currentUser)
        .map((evidence) -> {
          attachment.setEvidence(evidence);
          attachment.setUser(currentUser);
          return attachmentRepository.save(attachment);
        })
        .orElseThrow();
  }

  @Override
  public Resource getContent(@NonNull Attachment attachment) throws IOException {
    return storageService.retrieve(attachment.getStorageKey());
  }


  @Override
  public Attachment store(@NonNull MultipartFile file, String title, String description,
      @NonNull UUID speciesCaseExternalKey,
      UUID evidenceExternalKey, User currentUser)
      throws IOException, HttpMediaTypeException {
    String originalFilename = file.getOriginalFilename();
    String contentType = file.getContentType();
    String key = storageService.store(file);
    Attachment attachment = new Attachment();
    attachment.setTitle(title);
    attachment.setDescription(description);
    attachment.setName((originalFilename != null) ? originalFilename : UNTITLED_FILENAME);
    attachment.setMimeType(
        (contentType != null) ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    attachment.setStorageKey(key);
    return save(attachment, speciesCaseExternalKey, evidenceExternalKey, currentUser);
  }
}
