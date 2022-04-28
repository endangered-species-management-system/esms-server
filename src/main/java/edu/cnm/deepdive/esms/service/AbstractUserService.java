package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface AbstractUserService {

  User getOrCreate(String oauthKey, String displayName);

  User getCurrentUser();

  User updateUser(User received);

  Iterable<User> getAll();

  Optional<User> getByExternalKey(UUID externalKey);
}

