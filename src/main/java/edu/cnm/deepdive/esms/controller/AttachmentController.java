package edu.cnm.deepdive.esms.controller;

import edu.cnm.deepdive.esms.model.entity.Attachment;
import edu.cnm.deepdive.esms.service.AbstractAttachmentService;
import edu.cnm.deepdive.esms.service.AbstractEvidenceService;
import edu.cnm.deepdive.esms.service.AbstractUserService;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cases/{speciesCaseId}/evidences/{evidenceId}/attachments")
public class AttachmentController {

  private final AbstractAttachmentService attachmentService;
  private final AbstractUserService userService;
  private final AbstractEvidenceService evidenceService;

  public AttachmentController(AbstractAttachmentService attachmentService,
      AbstractUserService userService, AbstractEvidenceService evidenceService) {
    this.attachmentService = attachmentService;
    this.userService = userService;
    this.evidenceService = evidenceService;
  }

  /**
   * Stores uploaded file content along with a new {@link Attachment} instance referencing the
   * content.
   *
   * @param title       Summary of uploaded content.
   * @param description Detailed description of uploaded content.
   * @param file        MIME content of single file upload.
   * @return Instance of {@link Attachment} created &amp; persisted for the uploaded content.
   */
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Attachment> post(
      @PathVariable UUID evidenceId,
      @PathVariable UUID speciesCaseId,
      @RequestParam MultipartFile file,
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String description)
      throws IOException, HttpMediaTypeException {
    Attachment saved = attachmentService.store(file, title, description, speciesCaseId, evidenceId,
        userService.getCurrentUser());
    URI location = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder
                .methodOn(AttachmentController.class)
                .get(saved.getEvidence().getSpeciesCase().getExternalKey(),
                    saved.getEvidence().getExternalKey(),
                    saved.getExternalKey())
        )
        .toUri();
    return ResponseEntity
        .created(location)
        .body(saved);
  }

  @GetMapping(value = "/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Attachment get(@PathVariable UUID speciesCaseId, @PathVariable UUID evidenceId,
      @PathVariable UUID attachmentId) {
    return attachmentService.getAttachment(speciesCaseId, evidenceId, attachmentId,
        userService.getCurrentUser());
  }

  @DeleteMapping("/{attachmentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID speciesCaseId, @PathVariable UUID evidenceId,
      @PathVariable UUID attachmentId)
      throws IOException {
    attachmentService.delete(speciesCaseId, evidenceId, attachmentId, userService.getCurrentUser());

  }

}
