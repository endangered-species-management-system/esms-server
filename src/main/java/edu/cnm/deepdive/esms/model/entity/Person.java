package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@JsonInclude(Include.NON_NULL)
@Entity
@Table(
    indexes = {
        @Index(columnList = "hireDate"),
        @Index(columnList = "userName")
    }
)
public class Person {

  @Id
  @GeneratedValue
  @Column(name = "person_id", updatable = false, columnDefinition = "UUID")
  @JsonIgnore
  private UUID id;

  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private UUID externalKey;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date hireDate;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updated;

  @JsonIgnore
  @Column(nullable = false, updatable = false, unique = true)
  private String oauthKey;

  @Column(nullable = false, unique = true)
  private String userName;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
  private Researcher researcher;

  public UUID getId() {
    return id;
  }

  public UUID getExternalKey() {
    return externalKey;
  }

  public Date getHireDate() {
    return hireDate;
  }

  public Date getUpdated() {
    return updated;
  }

  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(String oauthKey) {
    this.oauthKey = oauthKey;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Researcher getResearcher() {
    return researcher;
  }

  public void setResearcher(Researcher researcher) {
    this.researcher = researcher;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var person = (Person) o;
    return Objects.equals(firstName, person.firstName) &&
        Objects.equals(lastName, person.lastName) &&
        Objects.equals(hireDate, person.hireDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), firstName, lastName, hireDate);
  }

  @Override
  public String toString() {
    return String.format("Person[username='%s', firstName='%s', lastName='%s', hiringDate='%s']\n",
        userName, firstName, lastName, hireDate == null ? "" : hireDate.toString());

  }
}
