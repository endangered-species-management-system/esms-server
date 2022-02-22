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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@SuppressWarnings("JpaDataSourceORMInspection")
@JsonInclude(Include.NON_NULL)
@Entity
public class Case {

  @Id
  @GeneratedValue
  @Column(name = "case_id", updatable = false, columnDefinition = "UUID")
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

  @Column(name = "case_number", unique = true, nullable = false)
  private String number;

  @Enumerated(EnumType.STRING)
  private Phase phase;

  @NotEmpty
  private String summary;

  private String detailedDescription;

  @ManyToOne
  @JoinColumn(name = "lead_researcher", nullable = false)
  private Researcher leadResearcher;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "case_researcher",
      joinColumns = @JoinColumn(name = "case_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "researcher_id", referencedColumnName = "id"))
  private Set<Researcher> assigned = new HashSet<>();

  public Case() {
    super();
    this.phase = Phase.SUBMITTED;
  }

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

  public Researcher getLeadInvestigator() {
    return leadResearcher;
  }

  public void setLeadInvestigator(Researcher leadInvestigator) {
    this.leadResearcher = leadInvestigator;
  }

  public Set<Researcher> getAssigned() {
    return assigned;
  }

 /* public void setAssigned(Set<Researcher> assigned) {
    assigned.forEach(this::addResearcher);
  }*/

  // TODO determine why this is not recognized
  //case is assigned to the researcher, always use this method
/*  public boolean addResearcher(Researcher researcher) {
    researcher.addCase(this);
    return assigned.add(researcher);
  }*/
}
