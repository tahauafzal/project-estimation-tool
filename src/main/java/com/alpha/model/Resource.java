package com.alpha.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Resource name is required")
    private String name;
    
    @NotNull(message = "Hourly rate is required")
    @Positive(message = "Hourly rate must be positive")
    private Double hourlyRate;
    
    @NotNull(message = "Allocated hours are required")
    @PositiveOrZero(message = "Allocated hours cannot be negative")
    private Double allocatedHours;
    
    @Enumerated(EnumType.STRING)
    private ResourceType type;
    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    public Resource() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
    public Double getAllocatedHours() { return allocatedHours; }
    public void setAllocatedHours(Double allocatedHours) { this.allocatedHours = allocatedHours; }
    public ResourceType getType() { return type; }
    public void setType(ResourceType type) { this.type = type; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
}
