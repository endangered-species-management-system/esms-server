package edu.cnm.deepdive.esms.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.esms.model.entity.Attachment;
import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.service.AbstractUserService;
import edu.cnm.deepdive.esms.service.UserService;
import edu.cnm.deepdive.esms.util.Role;
import edu.cnm.deepdive.esms.view.UserView;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService service;

  @Autowired
  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
//  @JsonView(UserView.Private.class)
  public User get() {
    return service.getCurrentUser();
  }

  @GetMapping(value = "/{externalKey}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(@PathVariable UUID externalKey) {
    return service
        .getByExternalKey(externalKey)
        .orElseThrow();
  }

  @PutMapping(value = "/me",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Private.class)
  public User put(@RequestBody User user) {
    return service.updateUser(user);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> getAll() {
    return service.getAll();
  }

  @PutMapping(value = "/{id}/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public User put(@RequestBody Set<Role> roles, @PathVariable UUID id) {
    return service.updateRoles(id, roles);
  }

  @PutMapping(value = "/{id}/inactive", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public User put(@RequestBody boolean inactive, @PathVariable UUID id) {
    return service.updateInactive(id, inactive);
  }

  @GetMapping(value = "/me/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Attachment> getMyAttachments() {
    return service
        .getAttachments(service.getCurrentUser().getExternalKey());
  }
}
