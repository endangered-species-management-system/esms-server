package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.cnm.deepdive.esms.util.Status;
import edu.cnm.deepdive.esms.util.Title;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

public class Researcher {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "person_id", updatable = false, columnDefinition = "UUID")
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

//  @NotEmpty
  @Column(unique = true, nullable = false)
  private String accessCardID;

  @NonNull
  @OneToOne
  @JoinColumn(name = "person_id")
  private Person person;

  @NonNull
  @Enumerated(EnumType.STRING)
  private Title title;

  @NonNull
  @Enumerated(EnumType.STRING)
  private Status status = Status.ACTIVE;

  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public UUID getExternalKey() {
    return externalKey;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  @NonNull
  public Date getUpdated() {
    return updated;
  }

  public String getAccessCardID() {
    return accessCardID;
  }

  public void setAccessCardID(String accessCardID) {
    this.accessCardID = accessCardID;
  }

  @NonNull
  public Person getPerson() {
    return person;
  }

  public void setPerson(@NonNull Person person) {
    this.person = person;
  }

  @NonNull
  public Title getTitle() {
    return title;
  }

  public void setTitle(@NonNull Title title) {
    this.title = title;
  }

  @NonNull
  public Status getStatus() {
    return status;
  }

  public void setStatus(@NonNull Status status) {
    this.status = status;
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
