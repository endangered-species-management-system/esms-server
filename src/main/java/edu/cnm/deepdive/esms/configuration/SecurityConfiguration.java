package edu.cnm.deepdive.esms.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
@Profile("service")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final Converter<Jwt, ? extends AbstractAuthenticationToken> converter;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuerUri;

  @Value("${spring.security.oauth2.resourceserver.jwt.client-id}")
  private String clientId;

  public SecurityConfiguration(
      Converter<Jwt, ? extends AbstractAuthenticationToken> converter) {
    this.converter = converter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http
        .authorizeRequests((auth) ->
            auth
                .antMatchers(HttpMethod.PUT, "/users/*/roles")
                .hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.PUT, "/users/*/inactive")
                .hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.PUT, "/users/me/**")
                .authenticated()
                .antMatchers(HttpMethod.GET, "/users/me/**")
                .authenticated()
                .antMatchers(HttpMethod.PUT, "/users/*/**")
                .hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.GET, "/users/*/**")
                .hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.POST, "/cases/*/evidences/**")
                .hasAnyRole("RESEARCHER", "LEAD")
                .antMatchers(HttpMethod.PUT, "/cases/*/evidences/**")
                .hasRole("LEAD")
                .antMatchers(HttpMethod.DELETE, "/cases/*/evidences/**")
                .hasRole("LEAD")
                .antMatchers(HttpMethod.GET, "/cases/*/evidences/**")
                .hasAnyRole("LEAD", "ADMINISTRATOR", "RESEARCHER")
                .antMatchers(HttpMethod.POST, "/cases/**")
                .hasRole("LEAD")
                .antMatchers(HttpMethod.PUT, "/cases/**")
                .hasRole("LEAD")
                .antMatchers(HttpMethod.DELETE, "/cases/**")
                .hasRole("LEAD")
                .antMatchers(HttpMethod.GET, "/cases/**")
                .hasAnyRole("LEAD", "ADMINISTRATOR", "RESEARCHER")
                .anyRequest()
                .authenticated()
        )
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(converter);
  }

  public JwtDecoder jwtDecoder() {
    NimbusJwtDecoder decoder = JwtDecoders.fromIssuerLocation(issuerUri);
    OAuth2TokenValidator<Jwt> audienceValidator =
        new JwtClaimValidator<List<String>>(JwtClaimNames.AUD, (aud) -> aud.contains(clientId));
    OAuth2TokenValidator<Jwt> issuerValidator =
        JwtValidators.createDefaultWithIssuer(issuerUri);
    OAuth2TokenValidator<Jwt> combinedValidator =
        new DelegatingOAuth2TokenValidator<Jwt>(audienceValidator, issuerValidator);
    decoder.setJwtValidator(combinedValidator);
    return decoder;
  }
}
