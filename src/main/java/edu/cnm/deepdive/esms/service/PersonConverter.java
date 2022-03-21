/*
package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.Person;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


*/
/**
 * Performs Object-to-String and String-to-Object conversions between model data objects and a
 * String representation of those objects that is suitable for rendering.
 *//*


@Service
public class PersonConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final AbstractPersonService service;


  */
/**
   * Constructor for the {@link AbstractPersonService} class initializes and create objects from
   * included parameter.
   *
   * @param service
   *//*

  @Autowired
  public PersonConverter(AbstractPersonService service) {
    this.service = service;
  }


  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt source) {
    Person person = service.getOrCreate(source.getSubject(), source.getClaimAsString("name"),
        source.getClaimAsString("email"), source.getClaimAsString("given_name"),
        source.getClaimAsString("family_name"));
    Collection<SimpleGrantedAuthority> grants = (person.getResearcher() != null)
        ? person
        .getResearcher()
        .getRoles()
        .stream()
        .map((role) -> String.format("ROLE_%s", role.toString().toUpperCase()))
        .collect(Collectors.toList());
    :new LinkedList<>();
    grants.add(new SimpleGrantedAuthority("ROLE_USER"));

    return new UsernamePasswordAuthenticationToken(person, source.getTokenValue(), grants);
  }
}


*/
