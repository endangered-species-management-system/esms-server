package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.dao.PersonRepository;
import edu.cnm.deepdive.esms.model.entity.Person;
import java.util.Date;
import org.springframework.security.core.context.SecurityContextHolder;

public class PersonService implements AbstractPersonService {

  private final PersonRepository repository;

  public PersonService(PersonRepository repository) {
    this.repository = repository;
  }

  @Override
  public Person getOrCreate(String oauthKey, String personName) {
    return repository
        .findByOauthKey(oauthKey)
        .map((person) -> {
          person.setConnected(new Date());
          return repository.save(person);
        })
        .orElseGet(() -> {
          Person person = new Person();
          person.setOauthKey(oauthKey);
          person.setUserName(personName);
          person.setConnected(new Date());
          return repository.save(person);
        });
  }

  @Override
  public Person getCurrentPerson() {
    return (Person) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
  }

  @Override
  public Person updatePerson(Person received) {
    return repository
        .findById(getCurrentPerson().getId())
        .map((person) -> {
          //noinspection ConstantConditions
          if (received.getUserName() != null) {
            person.setUserName(received.getUserName());
          }
          return repository.save(person);
        })
        .orElseThrow();
  }
}