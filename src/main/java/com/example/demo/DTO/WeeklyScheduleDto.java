package com.example.demo.DTO;

import java.util.List;

public class WeeklyScheduleDto {
    private Long landPlotID;
    private String landPlotName;
    private List<ServiceDto1> services;

    public WeeklyScheduleDto(Long landPlotID, String landPlotName, List<ServiceDto1> services) {
        this.landPlotID = landPlotID;
        this.landPlotName = landPlotName;
        this.services = services;
    }

    // Getters và Setters
    public Long getLandPlotID() {
        return landPlotID;
    }
    public void setLandPlotID(Long landPlotID) {
        this.landPlotID = landPlotID;
    }
    public String getLandPlotName() {
        return landPlotName;
    }
    public void setLandPlotName(String landPlotName) {
        this.landPlotName = landPlotName;
    }
    public List<ServiceDto1> getServices() {
        return services;
    }
    public void setServices(List<ServiceDto1> services) {
        this.services = services;
    }
}

