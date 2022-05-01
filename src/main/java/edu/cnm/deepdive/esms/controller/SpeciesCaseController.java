package edu.cnm.deepdive.esms.controller;

import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import edu.cnm.deepdive.esms.service.AbstractUserService;
import edu.cnm.deepdive.esms.service.SpeciesCaseService;
import java.net.URI;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("service")
@RequestMapping("/cases")
public class SpeciesCaseController {

  private final SpeciesCaseService speciesCaseService;
  private final AbstractUserService userService;

  public SpeciesCaseController(SpeciesCaseService speciesCaseService,
      AbstractUserService userService) {
    this.speciesCaseService = speciesCaseService;
    this.userService = userService;
  }

  //  @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SpeciesCase> post(@RequestBody SpeciesCase speciesCase) {
    SpeciesCase newCase =
        speciesCaseService.addCase(speciesCase, userService.getCurrentUser());
    URI location = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder
                .methodOn(SpeciesCaseController.class)
                .get(speciesCase.getExternalKey())
        )
        .toUri();
    return ResponseEntity
        .created(location)
        .body(speciesCase);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public SpeciesCase get(@PathVariable UUID id) {
    return speciesCaseService.get(id);
  }
}
