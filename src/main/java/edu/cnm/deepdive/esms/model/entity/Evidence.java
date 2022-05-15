package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Evidence implements Comparable<Evidence> {

  @Id
  @GeneratedValue
  @Column(name = "evidence_id", updatable = false)
  @JsonIgnore
  private Long id;

  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private UUID externalKey;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @JsonProperty(access = Access.READ_ONLY)
  @Column(nullable = false, updatable = false)
  private Date created;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updated;

  @NonNull
  @Column(name = "evidence_name", nullable = false, updatable = false)
  private String name;

  @Column(unique = true)
  private String location;

  @Column(name = "evidence_number", unique = true, nullable = false)
  private String number;

  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "NVARCHAR(MAX)")
  private String note;

  @ManyToOne
  @JoinColumn(name = "case_id", nullable = false, updatable = false)
  @JsonIgnore
  private SpeciesCase speciesCase;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @OneToMany(mappedBy = "evidence", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
  @OrderBy("created DESC")
  @JsonIgnore
  private Set<Attachment> attachments = new LinkedHashSet<>();

  public Long getId() {
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

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public SpeciesCase getSpeciesCase() {
    return speciesCase;
  }

  public void setSpeciesCase(SpeciesCase speciesCase) {
    this.speciesCase = speciesCase;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Set<Attachment> getAttachments() {
    return attachments;
  }

  @Override
  public int hashCode() {
    return (id != null) ? id.hashCode() : 0;
  }

  @Override
  public boolean equals(Object obj) {
    boolean comparison;

    if (this == obj) {
      comparison = true;
    } else if (obj instanceof Evidence) {
      Evidence other = (Evidence) obj;
      comparison = (id != null && id.equals(other.id));
    } else {
      comparison = false;
    }
    return comparison;
  }

  @Override
  public String toString() {
    return number;
  }

  @Override
  public int compareTo(Evidence other) {
    return number.compareToIgnoreCase(other.number);
  }

  @PrePersist
  private void populateAdditionalFields() {
    externalKey = UUID.randomUUID();
    number = String.format("%1$s-%2$tY%2$tm%2$td%2$tH%2$tk%2$tM%2$tS",
        name.replaceAll("[\\W_]+", "-").toLowerCase(), created);
  }

}
