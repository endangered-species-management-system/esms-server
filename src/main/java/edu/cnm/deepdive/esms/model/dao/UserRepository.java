package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.util.Role;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByExternalKey(UUID externalKey);

  Optional<User> findByExternalKeyAndRolesContains(UUID externalKey, Role role);

  Optional<User> findByExternalKeyAndRolesContainsAndInactive(UUID externalKey, Role role, boolean inactive);

  Iterable<User> getAllByOrderByDisplayNameAsc();

  Iterable<User> getAllByInactiveOrderByDisplayNameAsc(boolean inactive);

  Optional<User> findByOauthKey(String oauthKey);

  Iterable<User> findByRolesContainingOrderByDisplayNameAsc(Role role);

  Iterable<User> findByRolesContainingAndInactiveOrderByDisplayNameAsc(Role role, boolean inactive);

}
