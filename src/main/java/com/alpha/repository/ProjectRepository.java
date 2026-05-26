package com.alpha.repository;

import com.alpha.model.Project;
import com.alpha.model.ProjectStatus;
import com.alpha.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCreatedBy(User user);
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByStartDateBetween(LocalDate start, LocalDate end);
    
    @Query("SELECT SUM(p.totalCostEstimate) FROM Project p")
    Double getTotalEstimatedCost();
}
