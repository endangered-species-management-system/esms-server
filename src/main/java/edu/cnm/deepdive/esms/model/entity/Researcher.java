package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.cnm.deepdive.esms.util.Status;
import edu.cnm.deepdive.esms.util.Role;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "person_id, card_id, created")
    }
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Researcher {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "researcher_id", updatable = false, columnDefinition = "UUID")
  @JsonIgnore
  private UUID id;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private UUID externalKey;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updated;

  @NotEmpty
  @Column(name = "card_id", unique = true, nullable = false)
  private String accessCardID;

  @NonNull
  @OneToOne
  @JoinColumn(name = "person_id")
  private Person person;

  @NonNull
  @Enumerated(EnumType.STRING)
  private Role role;
// TODO Consider changing to role with an enumset

  @Enumerated(EnumType.STRING)
  @ElementCollection
  @CollectionTable(name = "researcher_role", joinColumns = @JoinColumn(name = "researcher_id"))
  @Column(name = "role")
  private Set<Role> roles = EnumSet.noneOf(Role.class);

  @NonNull
  @Enumerated(EnumType.STRING)
  private Status status = Status.ACTIVE;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "assigned",
      cascade = {CascadeType.DETACH, CascadeType.MERGE,
          CascadeType.PERSIST, CascadeType.REFRESH})
  @OrderBy("number ASC")
  private Set<SpeciesCase> speciesCases = new HashSet<>();

  @OneToMany(mappedBy = "researcher", cascade = CascadeType.PERSIST)
  private Set<Track> trackSet = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public UUID getExternalKey() {
    return externalKey;
  }

  public Date getCreated() {
    return created;
  }

  public Date getUpdated() {
    return updated;
  }

  public String getAccessCardID() {
    return accessCardID;
  }

  public void setAccessCardID(String accessCardID) {
    this.accessCardID = accessCardID;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  @NonNull
  public Role getRole() {
    return role;
  }

  public void setRole(@NonNull Role role) {
    this.role = role;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Set<SpeciesCase> getCases() {
    return speciesCases;
  }

  public void setCases(Set<SpeciesCase> aSpeciesCases) {
    this.speciesCases = aSpeciesCases;
  }

  boolean addCase(SpeciesCase aSpeciesCase) {
    return speciesCases.add(aSpeciesCase);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Researcher)) {
      return false;
    }
    Researcher that = (Researcher) obj;
    return accessCardID.equals(that.accessCardID) && person.equals(that.person);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessCardID, person);
  }

}
