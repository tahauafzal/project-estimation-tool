package com.alpha.controller;

import com.alpha.model.Project;
import com.alpha.model.User;
import com.alpha.model.Role;
import com.alpha.service.ProjectService;
import com.alpha.service.EstimationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private EstimationService estimationService;
    
    @GetMapping
    public String listProjects(Model model) {
        // Create a mock user since we have no authentication
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("demo");
        mockUser.setRole(Role.PROJECT_MANAGER);
        
        model.addAttribute("projects", projectService.getProjectsByUser(mockUser));
        return "projects/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        return "projects/create";
    }
    
    @PostMapping("/create")
    public String createProject(@Valid @ModelAttribute Project project, 
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "projects/create";
        }
        
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("demo");
        mockUser.setRole(Role.PROJECT_MANAGER);
        
        Project savedProject = projectService.createProject(project, mockUser);
        redirectAttributes.addFlashAttribute("success", "Project created successfully");
        return "redirect:/projects/" + savedProject.getId();
    }
    
    @GetMapping("/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        model.addAttribute("estimate", estimationService.calculateDetailedEstimate(project));
        model.addAttribute("riskAssessment", estimationService.getRiskAssessment(project));
        return "projects/view";
    }
    
    @GetMapping("/{id}/estimate")
    @ResponseBody
    public Object getProjectEstimate(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return estimationService.calculateDetailedEstimate(project);
    }
}
