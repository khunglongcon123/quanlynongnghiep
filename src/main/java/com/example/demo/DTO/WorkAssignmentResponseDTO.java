package com.example.demo.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WorkAssignmentResponseDTO {
    private Long assignmentId;
    private String workMethod;
    private String details;
    private String status;
    private String serviceType;
    private String seasonCrop;
    private LocalDate seasonStartDate;
    private LocalDate seasonEndDate;
    private String landPlotName;
    private String landPlotLocation;
    private LocalDateTime dateStart; // Thời gian làm

    public WorkAssignmentResponseDTO(Long assignmentId, String workMethod, String details, String status,
                                     String serviceType, String seasonCrop, LocalDate seasonStartDate,
                                     LocalDate seasonEndDate, String landPlotName, String landPlotLocation, LocalDateTime dateStart) {
        this.assignmentId = assignmentId;
        this.workMethod = workMethod;
        this.details = details;
        this.status = status;
        this.serviceType = serviceType;
        this.seasonCrop = seasonCrop;
        this.seasonStartDate = seasonStartDate;
        this.seasonEndDate = seasonEndDate;
        this.landPlotName = landPlotName;
        this.landPlotLocation = landPlotLocation;
        this.dateStart = dateStart;
    }
}
