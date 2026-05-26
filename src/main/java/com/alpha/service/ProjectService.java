package com.alpha.service;

import com.alpha.model.*;
import com.alpha.repository.ProjectRepository;
import com.alpha.repository.TaskRepository;
import com.alpha.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }
    
    @Transactional
    public Project createProject(Project project, User createdBy) {
        project.setCreatedBy(createdBy);
        project.setStatus(ProjectStatus.PLANNING);
        project.setTotalCostEstimate(0.0);
        project.setTotalHoursEstimate(0.0);
        return projectRepository.save(project);
    }
    
    @Transactional
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }
    
    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
    
    @Transactional
    public void calculateProjectEstimates(Long projectId) {
        Project project = getProjectById(projectId);
        
        Double totalHours = taskRepository.getTotalEstimatedHoursByProject(project);
        project.setTotalHoursEstimate(totalHours != null ? totalHours : 0.0);
        
        Double totalCost = resourceRepository.getTotalCostByProject(project);
        project.setTotalCostEstimate(totalCost != null ? totalCost : 0.0);
        
        projectRepository.save(project);
    }
    
    public List<Project> getProjectsByUser(User user) {
        return projectRepository.findByCreatedBy(user);
    }
}
