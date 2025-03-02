package com.example.demo.DTO;

import java.time.LocalDate;
import java.util.List;

public class MonthlyScheduleDto {
    private Long seasonID;
    private String landPlotName;
    private String crop;
    private String tenCay;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ServiceDto1> services;

    public MonthlyScheduleDto(Long seasonID, String landPlotName, String crop, String tenCay,
                              LocalDate startDate, LocalDate endDate, List<ServiceDto1> services) {
        this.seasonID = seasonID;
        this.landPlotName = landPlotName;
        this.crop = crop;
        this.tenCay = tenCay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.services = services;
    }

    // Getters và Setters
    public Long getSeasonID() {
        return seasonID;
    }
    public void setSeasonID(Long seasonID) {
        this.seasonID = seasonID;
    }
    public String getLandPlotName() {
        return landPlotName;
    }
    public void setLandPlotName(String landPlotName) {
        this.landPlotName = landPlotName;
    }
    public String getCrop() {
        return crop;
    }
    public void setCrop(String crop) {
        this.crop = crop;
    }
    public String getTenCay() {
        return tenCay;
    }
    public void setTenCay(String tenCay) {
        this.tenCay = tenCay;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public List<ServiceDto1> getServices() {
        return services;
    }
    public void setServices(List<ServiceDto1> services) {
        this.services = services;
    }
}

