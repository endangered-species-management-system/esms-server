package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.Person;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, UUID> {

  Optional<Person> findByExternalKey(UUID externalKey);

  Optional<Person> findByOauthKey(String oauthKey);
}
