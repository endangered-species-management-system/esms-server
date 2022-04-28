package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.UserRepository;
import edu.cnm.deepdive.esms.model.entity.User;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AbstractUserService {

  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User getOrCreate(String oauthKey, String displayName) {
    return repository
        .findByOauthKey(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setDisplayName(displayName);
          user.setConnected(Instant.now());
          return repository.save(user);
        });
  }

  @Override
  public User getCurrentUser() {
    return (User) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
  }


  @Override
  public Iterable<User> getAll() {
    return repository.getAllByOrderByDisplayNameAsc();
  }

  @Override
  public Optional<User> getByExternalKey(UUID key) {
    return repository.findByExternalKey(key);
  }

  @Override
  public User updateUser(User received) {
    return repository
        .findById(getCurrentUser().getId())
        .map((user) -> {
          //noinspection ConstantConditions
          if (received.getDisplayName() != null) {
            user.setDisplayName(received.getDisplayName());
          }
            user.setFirstName(received.getFirstName());
            user.setLastName(received.getLastName());
          return repository.save(user);
        })
        .orElseThrow();
  }
}