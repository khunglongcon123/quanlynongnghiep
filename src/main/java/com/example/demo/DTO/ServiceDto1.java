package com.example.demo.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceDto1 {
    private Long serviceID;
    private String serviceType;
    private String description;
    private LocalDateTime dateStart;
    private LocalDateTime endTime;
    private List<EmployeeDto> employees;

    public ServiceDto1(Long serviceID, String serviceType, String description,
                      LocalDateTime dateStart, LocalDateTime endTime, List<EmployeeDto> employees) {
        this.serviceID = serviceID;
        this.serviceType = serviceType;
        this.description = description;
        this.dateStart = dateStart;
        this.endTime = endTime;
        this.employees = employees;
    }

    // Getters và Setters
    public Long getServiceID() {
        return serviceID;
    }
    public void setServiceID(Long serviceID) {
        this.serviceID = serviceID;
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
    public LocalDateTime getDateStart() {
        return dateStart;
    }
    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public List<EmployeeDto> getEmployees() {
        return employees;
    }
    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }
}
