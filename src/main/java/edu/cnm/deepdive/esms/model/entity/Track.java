package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.cnm.deepdive.esms.util.TrackType;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Track {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "track_id", updatable = false, columnDefinition = "UUID")
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

  @ManyToOne
  @JoinColumn(name = "track_evidence")
  private Evidence evidence;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "track_researcher", nullable = false)
  private Researcher researcher;

  @NonNull
  @Enumerated(EnumType.STRING)
  private TrackType trackType;

  @NotEmpty
  private String purpose;

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

  public Evidence getEvidence() {
    return evidence;
  }

  public void setEvidence(Evidence evidence) {
    this.evidence = evidence;
  }

  @NonNull
  public Researcher getResearcher() {
    return researcher;
  }

  public void setResearcher(@NonNull Researcher researcher) {
    this.researcher = researcher;
  }

  @NonNull
  public TrackType getTrackType() {
    return trackType;
  }

  public void setTrackType(@NonNull TrackType trackType) {
    this.trackType = trackType;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }
}
