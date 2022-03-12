package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.cnm.deepdive.esms.util.Phase;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;


@SuppressWarnings("JpaDataSourceORMInspection")
@Table(
    indexes = {
        @Index(columnList = "case_number, created")
    }
)
@Entity
public class SpeciesCase {

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "case_id", updatable = false, nullable = false, columnDefinition = "UUID")
  @JsonIgnore
  private UUID id;

  @NonNull
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


  @Column(name="case_number", unique = true, nullable = false, updatable = false)
  private String number;

  @Column(unique = true, nullable = false)
  private String speciesName;

  @Enumerated(EnumType.STRING)
  private Phase phase = Phase.SUBMITTED;

  @NotEmpty
  private String summary;

  private String detailedDescription;

  @ManyToOne(optional = true) // FIXME make non-nullable when appropriate
  @JoinColumn(name = "lead_researcher", nullable = true)
  private Researcher leadResearcher;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
      CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "case_researcher",
      joinColumns = @JoinColumn(name = "case_id"),
      inverseJoinColumns = @JoinColumn(name = "researcher_id"))
  private final Set<Researcher> assigned = new HashSet<>();

  @OneToMany(mappedBy = "speciesCase", cascade = CascadeType.PERSIST)
  private Set<Evidence> evidenceSet = new HashSet<>();

  public SpeciesCase() {
    super();
    this.phase = Phase.SUBMITTED;
  }

  public UUID getId() {
    return id;
  }

  @NonNull
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

  public String getSpeciesName() {
    return speciesName;
  }

  public void setSpeciesName(String speciesName) {
    this.speciesName = speciesName;
  }

  public Phase getPhase() {
    return phase;
  }

  public void setPhase(Phase phase) {
    this.phase = phase;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getDetailedDescription() {
    return detailedDescription;
  }

  public void setDetailedDescription(String detailedDescription) {
    this.detailedDescription = detailedDescription;
  }

  public Researcher getLeadResearcher() {
    return leadResearcher;
  }

  public void setLeadResearcher(Researcher leadInvestigator) {
    this.leadResearcher = leadInvestigator;
  }

  public Set<Researcher> getAssigned() {
    return assigned;
  }

  @PrePersist
  private void setGeneratedValues() {
    externalKey = UUID.randomUUID();
  }

}
