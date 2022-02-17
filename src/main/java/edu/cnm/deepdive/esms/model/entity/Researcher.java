package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.cnm.deepdive.esms.util.Status;
import edu.cnm.deepdive.esms.util.Title;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
  private Date created;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updated;

  @NotEmpty
  @Column(name = "card_id", unique = true, nullable = false)
  private String accessCardID;

  @OneToOne
  @JoinColumn(name = "person_id")
  private Person person;

  @Enumerated(EnumType.STRING)
  private Title title;

  @Enumerated(EnumType.STRING)
  private Status status = Status.ACTIVE;

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

  public void setPerson( Person person) {
    this.person = person;
  }

  public Title getTitle() {
    return title;
  }

  public void setTitle( Title title) {
    this.title = title;
  }

  
  public Status getStatus() {
    return status;
  }

  public void setStatus( Status status) {
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
