package com.alpha.repository;

import com.alpha.model.Task;
import com.alpha.model.TaskStatus;
import com.alpha.model.Project;
import com.alpha.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
    List<Task> findByAssignedTo(User user);
    List<Task> findByStatus(TaskStatus status);
    
    @Query("SELECT SUM(t.estimatedHours) FROM Task t WHERE t.project = ?1")
    Double getTotalEstimatedHoursByProject(Project project);
}
