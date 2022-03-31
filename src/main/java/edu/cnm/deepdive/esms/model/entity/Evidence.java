package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Evidence {

  @Id
  @GeneratedValue
  @Column(name = "evidence_id", updatable = false, columnDefinition = "UUID")
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

  @Column(name = "evidence_number", unique = true, nullable = false)
  private String number;

  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "NVARCHAR(MAX)")
  private String note;

  @Column(nullable = false, updatable = false)
  private Boolean archived;

  @Column(nullable = false, updatable = false)
  private Boolean notImage;

  @NonNull
  @Column(name = "image_name", nullable = false, updatable = false)
  private String name;

  @NonNull
  @JsonIgnore
  @Column(name = "resource_key", nullable = false, updatable = false)
  private String key;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String contentType;

  @ManyToOne
  @JoinColumn(nullable = false)
  private SpeciesCase speciesCase;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Storage storage;

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

  public Boolean getArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public Boolean getNotImage() {
    return notImage;
  }

  public void setNotImage(Boolean notImage) {
    this.notImage = notImage;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  public String getKey() {
    return key;
  }

  public void setKey(@NonNull String key) {
    this.key = key;
  }

  @NonNull
  public String getContentType() {
    return contentType;
  }

  public void setContentType(@NonNull String contentType) {
    this.contentType = contentType;
  }

  public SpeciesCase getSpeciesCase() {
    return speciesCase;
  }

  public void setSpeciesCase(SpeciesCase speciesCase) {
    this.speciesCase = speciesCase;
  }

  public Storage getStorage() {
    return storage;
  }

  public void setStorage(Storage storage) {
    this.storage = storage;
  }
}
