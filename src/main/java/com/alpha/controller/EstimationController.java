package com.alpha.controller;

import com.alpha.model.Project;
import com.alpha.service.ProjectService;
import com.alpha.service.EstimationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/estimations")
public class EstimationController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private EstimationService estimationService;
    
    @GetMapping("/project/{projectId}")
    public String viewEstimation(@PathVariable Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("estimate", estimationService.calculateDetailedEstimate(project));
        model.addAttribute("tasks", project.getTasks());
        return "estimations/view";
    }
    
    @PostMapping("/calculate")
    @ResponseBody
    public Map<String, Object> calculateEstimation(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        double hours = Double.parseDouble(params.get("hours").toString());
        double hourlyRate = Double.parseDouble(params.get("hourlyRate").toString());
        
        result.put("cost", hours * hourlyRate);
        result.put("hours", hours);
        result.put("recommendedBuffer", hours * 0.2);
        result.put("recommendedContingency", hours * 0.15);
        
        return result;
    }
}
