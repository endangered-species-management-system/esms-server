package edu.cnm.deepdive.esms.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.service.AbstractUserService;
import edu.cnm.deepdive.esms.view.UserView;
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

  private final AbstractUserService service;

  @Autowired
  public UserController(AbstractUserService service) {
    this.service = service;
  }

/*  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Private.class)
  public User get() {
    return service.getCurrentUser();
  }*/

/*
  @PutMapping(value = "/me",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(UserView.Private.class)
  public User put(@RequestBody User user) {
    return service.updateUser(user);
  }
*/

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<User> getAll() {
    return service.getAll();
  }

  // TODO change the role

}
