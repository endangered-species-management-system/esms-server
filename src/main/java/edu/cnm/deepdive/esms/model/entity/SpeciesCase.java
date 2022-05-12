package edu.cnm.deepdive.esms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.cnm.deepdive.esms.model.dao.SpeciesCaseRepository;
import edu.cnm.deepdive.esms.util.Phase;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
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

  @Id
  @GeneratedValue
  @Column(name = "case_id", updatable = false, nullable = false)
  @JsonIgnore
  private Long id;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private UUID externalKey;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private Date created;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private Date updated;

  @Column(name = "case_number", unique = true, nullable = false, updatable = false)
  private String number;

  @Column(unique = true, nullable = false)
  private String speciesName;

  @Enumerated(EnumType.STRING)
  private Phase phase;

  @NotEmpty
  private String summary;

  @Column(columnDefinition = "TEXT")
  private String detailedDescription;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "lead_id", nullable = false)
  private User leadResearcher;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cases")
  @JsonIgnore
  private final Set<User> assigned = new LinkedHashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "speciesCase", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private Set<Evidence> evidences = new LinkedHashSet<>();

  public SpeciesCase() {
    super();
    this.phase = Phase.SUBMITTED;
  }

  public Long getId() {
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

  public User getLeadResearcher() {
    return leadResearcher;
  }

  public void setLeadResearcher(User leadInvestigator) {
    this.leadResearcher = leadInvestigator;
  }

  public Set<User> getAssigned() {
    return assigned;
  }

  public Set<Evidence> getEvidences() {
    return evidences;
  }

  @Override
  public boolean equals(Object obj) {
    boolean comparison;

    if (this == obj) {
      comparison = true;
    } else if (obj instanceof SpeciesCase) {
      SpeciesCase other = (SpeciesCase) obj;
      comparison = (id != null && id.equals(other.id));
    } else {
      comparison = false;
    }
    return comparison;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return number;
  }

  @PrePersist
  private void setGeneratedValues() {
    externalKey = UUID.randomUUID();
    number = String.format("%1$s-%2$tY%2$tm%2$td%2$tH%2$tk%2$tM%2$tS",
        speciesName.replaceAll("[\\W_]+", "-").toLowerCase(), new Date());
  }

}
