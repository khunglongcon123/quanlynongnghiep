package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class WorkAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentID;
    @ManyToOne
    @JoinColumn(name = "serviceID", nullable = false)
    @JsonBackReference
    private Service service;
    @ManyToOne
    @JoinColumn(name = "workMethodID", nullable = false)
    private WorkMethod workMethod;
    @ManyToOne
    @JoinColumn(name = "employeeID", nullable = true)
    private Employee employee;

    private String details;
    private String status;
    private String note;

    // Getters and Setters
    public Long getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(Long assignmentID) {
        this.assignmentID = assignmentID;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public WorkMethod getWorkMethod() {
        return workMethod;
    }

    public void setWorkMethod(WorkMethod workMethod) {
        this.workMethod = workMethod;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
}
