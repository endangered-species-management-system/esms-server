package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.Person;

public interface AbstractPersonService {

  Person getOrCreate(String oauthKey, String displayName);

  Person getCurrentPerson();

  Person updatePerson(Person received);
  
}
