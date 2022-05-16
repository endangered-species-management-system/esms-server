package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Attachment {

  @Id
  @GeneratedValue
  @Column(name = "attachment_id", updatable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private UUID externalKey;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private Date created;

  @Column(nullable = false, updatable = false)
  private String title;

  @Column(updatable = false)
  private String description;

  @NonNull
  @Column(nullable = false)
  @JsonIgnore
  private String storageKey;

  @NonNull
  @Column(name = "file_name", nullable = false, updatable = false)
  private String name;

  @NonNull
  @Column(nullable = false)
  private String mimeType;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "evidence_id")
  @JsonIgnore
  private Evidence evidence;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  public Long getId() {
    return id;
  }

  public UUID getExternalKey() {
    return externalKey;
  }

  public Date getCreated() {
    return created;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @NonNull
  public String getStorageKey() {
    return storageKey;
  }

  public void setStorageKey(@NonNull String path) {
    this.storageKey = path;
  }

  @NonNull
  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(@NonNull String mimeType) {
    this.mimeType = mimeType;
  }

  @NonNull
  public Evidence getEvidence() {
    return evidence;
  }

  public void setEvidence(@NonNull Evidence evidence) {
    this.evidence = evidence;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  @PrePersist
  private void generateExternalKey() {
    externalKey = UUID.randomUUID();
  }
}
