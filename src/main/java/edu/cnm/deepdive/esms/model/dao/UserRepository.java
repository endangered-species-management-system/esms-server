package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByExternalKey(UUID externalKey);

  Iterable<User> getAllByOrderByLastNameAsc();

  Optional<User> findByOauthKey(String oauthKey);
}
