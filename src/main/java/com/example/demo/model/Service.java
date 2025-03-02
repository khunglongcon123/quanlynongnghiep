package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceID;

    @ManyToOne
    @JoinColumn(name = "seasonID", nullable = false)
    @JsonIgnore
    private Season season;
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkAssignment> workAssignments = new ArrayList<>();

    public List<WorkAssignment> getWorkAssignments() {
        return workAssignments;
    }

    public void setWorkAssignments(List<WorkAssignment> workAssignments) {
        this.workAssignments = workAssignments;
    }


    private String serviceType;
    private String description;
    private Double cost;
    private LocalDateTime dateStart; // Dùng LocalDateTime thay vì LocalDate

    // Constructor
    public Service() {}

    public Service(Season season, String serviceType, String description, Double cost, LocalDateTime dateStart) {
        this.season = season;
        this.serviceType = serviceType;
        this.description = description;
        this.cost = cost;
        this.dateStart = dateStart;
    }

    // Getters and Setters
    public Long getServiceID() {
        return serviceID;
    }

    public void setServiceID(Long serviceID) {
        this.serviceID = serviceID;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    // Lấy thời gian kết thúc của dịch vụ (Mặc định là +4 giờ)
    public LocalDateTime getEndTime() {
        return this.dateStart.plusHours(4);
    }
}
