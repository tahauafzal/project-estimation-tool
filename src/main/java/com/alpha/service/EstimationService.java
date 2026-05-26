package com.alpha.service;

import com.alpha.model.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class EstimationService {
    
    public Map<String, Object> calculateDetailedEstimate(Project project) {
        Map<String, Object> estimate = new HashMap<>();
        
        double totalHours = project.getTotalHoursEstimate();
        double totalCost = project.getTotalCostEstimate();
        
        double bufferHours = totalHours * 0.2;
        double bufferCost = totalCost * 0.2;
        
        double contingencyHours = totalHours * 0.15;
        double contingencyCost = totalCost * 0.15;
        
        estimate.put("totalHours", totalHours);
        estimate.put("totalCost", totalCost);
        estimate.put("bufferHours", bufferHours);
        estimate.put("bufferCost", bufferCost);
        estimate.put("contingencyHours", contingencyHours);
        estimate.put("contingencyCost", contingencyCost);
        estimate.put("finalHours", totalHours + bufferHours + contingencyHours);
        estimate.put("finalCost", totalCost + bufferCost + contingencyCost);
        
        return estimate;
    }
    
    public String getRiskAssessment(Project project) {
        double totalHours = project.getTotalHoursEstimate();
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
            project.getStartDate(), project.getEndDate()
        );
        
        if (totalHours > daysBetween * 8) {
            return "HIGH RISK: Estimated hours exceed available working days";
        } else if (totalHours > daysBetween * 6) {
            return "MEDIUM RISK: Tight schedule, consider adding resources";
        } else {
            return "LOW RISK: Schedule appears achievable";
        }
    }
}
