package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.util.Role;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByExternalKey(UUID externalKey);

  Iterable<User> getAllByOrderByLastNameAsc();

  Optional<User> findByOauthKey(String oauthKey);

  @Query("select u from User AS u JOIN u.researcher AS r WHERE :role IN r.roles")
  Iterable<User> findAllWithRole(Role role);
}
