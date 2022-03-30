package edu.cnm.deepdive.esms.model.dao;

import edu.cnm.deepdive.esms.model.entity.Researcher;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, UUID> {

}
