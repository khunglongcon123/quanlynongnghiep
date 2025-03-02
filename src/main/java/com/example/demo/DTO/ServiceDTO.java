package com.example.demo.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ServiceDTO {
    private Long seasonId;

    private String serviceType;
    private String description;
    private Double cost;
    private LocalDateTime dateStart = LocalDateTime.now();


    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeason(Long season) {
        this.seasonId = season;
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

}
