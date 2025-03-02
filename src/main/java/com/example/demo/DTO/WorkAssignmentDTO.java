package com.example.demo.DTO;

public class WorkAssignmentDTO {
    private Long serviceId;
    private Long workMethodId;
    private Long employeeId;
    private String details;
    private String status;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getWorkMethodId() {
        return workMethodId;
    }

    public void setWorkMethodId(Long workMethodId) {
        this.workMethodId = workMethodId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;
}
