package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.Attachment;
import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.util.Role;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AbstractUserService {

  User getOrCreate(String oauthKey, String displayName);

  User getCurrentUser();

  User updateUser(User received);

  User updateRoles(UUID externalKey, Set<Role> roles);

  User updateInactive(UUID externalKey, boolean inactive);

  Iterable<Attachment> getAttachments(UUID externalKey);

  User updateInactive(UUID externalKey, Boolean inactive);

  Iterable<User> getAll();

  Optional<User> getByExternalKey(UUID externalKey);


}

