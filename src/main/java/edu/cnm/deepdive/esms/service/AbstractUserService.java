package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.User;

public interface AbstractUserService {

  User getOrCreate(String oauthKey, String displayName);

  User getCurrentUser();

  User updateUser(User received);

  Iterable<User> getAll();
}
