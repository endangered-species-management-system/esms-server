package edu.cnm.deepdive.esms.service;

import edu.cnm.deepdive.esms.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * Performs Object-to-String and String-to-Object conversions between model data objects and a
 * String representation of those objects that is suitable for rendering.
 */


@Service
public class UserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserService service;

  @Autowired
  public UserConverter(UserService service) {
    this.service = service;
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt source) {
    User user = service.getOrCreate(source.getSubject(), source.getClaimAsString("name"));
    if (user.isInactive()) {
      throw new DisabledException("Account has been deactivated");
    }
    Collection<SimpleGrantedAuthority> grants =
        Stream
            .concat(
                Stream.of("USER"),
                user
                    .getRoles()
                    .stream()
                    .map(Enum::toString)
            )
            .map((s) -> "ROLE_" + s)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toUnmodifiableList());
    return new UsernamePasswordAuthenticationToken(user, source.getTokenValue(), grants);
  }
}



