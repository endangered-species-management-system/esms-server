package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.UserRepository;
import edu.cnm.deepdive.esms.model.entity.User;
import java.util.Date;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AbstractUserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User getOrCreate(String oauthKey, String userName) {
    return repository
        .findByOauthKey(oauthKey)
        .map((user) -> {
          user.setConnected(new Date());
          return repository.save(user);
        })
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setUserName(userName);
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
  public User updateUser(User received) {
    return repository
        .findById(getCurrentUser().getId())
        .map((user) -> {
          //noinspection ConstantConditions
          if (received.getUserName() != null) {
            user.setUserName(received.getUserName());
          }
          return repository.save(user);
        })
        .orElseThrow();
  }
}