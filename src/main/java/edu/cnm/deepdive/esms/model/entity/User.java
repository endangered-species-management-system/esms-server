package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.cnm.deepdive.esms.util.Role;
import java.time.Instant;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
//@JsonInclude(Include.NON_NULL)
@Entity
@Table(
    name = "user_profile",
    indexes = {
        @Index(columnList = "displayName")
    }
)
public class User {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "user_id", updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private UUID externalKey;

  @NonNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @JsonIgnore
  private Date connected;

  @NonNull
  @JsonIgnore
  @Column(nullable = false, updatable = false, unique = true, length = 30)
  private String oauthKey;

  @NonNull
  @Column(nullable = false)
  private String displayName;

  @NonNull
  private String firstName;

  @NonNull
  private String lastName;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role")
  private Set<Role> roles = EnumSet.noneOf(Role.class);

  private boolean inactive;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
      CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(name = "user_case",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "case_id"))
  @OrderBy("number ASC")
  @JsonIgnore
  private final List<SpeciesCase> cases = new LinkedList<>();

  @OneToMany(mappedBy = "leadResearcher", fetch = FetchType.LAZY)
  @OrderBy("number ASC")
  @JsonIgnore
  private final Set<SpeciesCase> casesLead = new LinkedHashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JsonIgnore
  private final Set<Attachment> attachments = new LinkedHashSet<>();

  @NonNull
  public Long getId() {
    return id;
  }

  @NonNull
  public UUID getExternalKey() {
    return externalKey;
  }

  public void setExternalKey(@NonNull UUID externalKey) {
    this.externalKey = externalKey;
  }

  @NonNull
  public Date getConnected() {
    return connected;
  }

  public void setConnected(@NonNull Date connected) {
    this.connected = connected;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(@NonNull String userName) {
    this.displayName = userName;
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

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public boolean isInactive() {
    return inactive;
  }

  public void setInactive(boolean inactive) {
    this.inactive = inactive;
  }

  public List<SpeciesCase> getCases() {
    return cases;
  }

  public Set<SpeciesCase> getCasesLead() {
    return casesLead;
  }

  public Set<Attachment> getAttachments() {
    return attachments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var user = (User) o;
    return Objects.equals(firstName, user.firstName) &&
        Objects.equals(lastName, user.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), firstName, lastName);
  }

  @Override
  public String toString() {
    return String.format("User[username='%s', firstName='%s', lastName='%s']\n",
        displayName, firstName, lastName);
  }

  @PrePersist
  private void generateExternalKey() {
    externalKey = UUID.randomUUID();
  }
}
