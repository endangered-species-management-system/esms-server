package edu.cnm.deepdive.esms.controller;

import edu.cnm.deepdive.esms.model.entity.Evidence;
import edu.cnm.deepdive.esms.service.AbstractEvidenceService;
import edu.cnm.deepdive.esms.service.AbstractUserService;
import java.net.URI;
import java.util.UUID;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cases/{speciesCaseId}/evidences")
public class EvidenceController {

  private final AbstractUserService userService;
  private final AbstractEvidenceService evidenceService;

  public EvidenceController(AbstractUserService userService,
      AbstractEvidenceService evidenceService) {
    this.userService = userService;
    this.evidenceService = evidenceService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Evidence> post(@PathVariable UUID speciesCaseId,
      @RequestBody Evidence evidence) {
    Evidence saved = evidenceService.addEvidence(speciesCaseId, evidence,
        userService.getCurrentUser());
    URI location = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder
                .methodOn(EvidenceController.class)
                .get(saved.getSpeciesCase().getExternalKey(), saved.getExternalKey())
        )
        .toUri();
    return ResponseEntity
        .created(location)
        .body(saved);

  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Evidence> get(@PathVariable UUID speciesCaseId) {
    return evidenceService
        .getEvidences(speciesCaseId, userService.getCurrentUser());
  }

  @GetMapping(value = "/{evidenceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Evidence get(@PathVariable UUID speciesCaseId, @PathVariable UUID evidenceId) {
    return evidenceService.getEvidence(speciesCaseId, evidenceId, userService.getCurrentUser());
  }

  @DeleteMapping("/{evidenceId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID speciesCaseId, @PathVariable UUID evidenceId) {
    evidenceService.deleteEvidence(speciesCaseId, evidenceId, userService.getCurrentUser());
  }
}
