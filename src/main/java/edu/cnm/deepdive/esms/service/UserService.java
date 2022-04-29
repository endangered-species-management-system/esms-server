package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.UserRepository;
import edu.cnm.deepdive.esms.model.entity.Attachment;
import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.util.Role;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
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
          user.setConnected(new Date());
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

  @Override
  public User updateRoles(UUID externalKey, Set<Role> roles) {
    return repository
        .findByExternalKey(externalKey)
        .map((user) -> {
          user.getRoles().clear();
          user.getRoles().addAll(roles);
          return repository.save(user);
        })
        .orElseThrow();
  }

  @Override
  @Transactional
  public Iterable<Attachment> getAttachments(UUID externalKey) {
    return repository
        .findByExternalKey(externalKey)
        .map(User::getAttachments)
        .orElseThrow();
  }

  @Override
  public User updateInactive(UUID externalKey, Boolean inactive) {
    return repository
        .findByExternalKey(externalKey)
        .map((user) -> {
          user.setInactive(inactive);
          return repository.save(user);
        })
        .orElseThrow();
  }
}