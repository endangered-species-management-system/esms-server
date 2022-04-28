package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.User;
import edu.cnm.deepdive.esms.util.Role;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByExternalKey(UUID externalKey);

  Iterable<User> getAllByOrderByDisplayNameAsc();

  Optional<User> findByOauthKey(String oauthKey);

  Iterable<User> findByRolesContaining(Role role);

}
