package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workMethodID;

    private String name;
    private String description;

    @OneToMany(mappedBy = "workMethod", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonBackReference
    private List<WorkAssignment> workAssignments = new ArrayList<>();

    // Getters and Setters
    public Long getWorkMethodID() {
        return workMethodID;
    }

    public void setWorkMethodID(Long workMethodID) {
        this.workMethodID = workMethodID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

