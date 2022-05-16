package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.Attachment;
import edu.cnm.deepdive.esms.model.entity.Evidence;
import edu.cnm.deepdive.esms.model.entity.User;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

public interface AbstractAttachmentService {

  Optional<Attachment> get(@NonNull UUID speciesCaseExternalKey, UUID evidenceExternalKey,
      UUID attachmentExternalKey);

  Attachment getAttachment(UUID speciesExternalKey, UUID evidenceExternalKey,
      UUID attachmentExternalKey, User currentUser);

  Iterable<Attachment> list(@NonNull UUID speciesCaseExternalKey,
      @NonNull UUID evidenceExternalKey);

  void delete(@NonNull UUID speciesCaseExternalKey, UUID evidenceExternalKey,
      UUID attachmentExternalKey, User currentUser) throws IOException;

  Resource getContent(@NonNull Attachment attachment) throws IOException;

  Attachment store(@NonNull MultipartFile file, String title, String description,
       UUID speciesCaseExternalKey,
      UUID evidenceExternalKey, User currentUser)
      throws IOException, HttpMediaTypeException;
}
