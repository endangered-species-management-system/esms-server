package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.SpeciesCase;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenceRepository extends JpaRepository<SpeciesCase, Long> {

}