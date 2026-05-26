package com.alpha.repository;

import com.alpha.model.Resource;
import com.alpha.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByProject(Project project);
    
    @Query("SELECT SUM(r.hourlyRate * r.allocatedHours) FROM Resource r WHERE r.project = ?1")
    Double getTotalCostByProject(Project project);
}
