package com.example.demo.DTO;

public class EmployeeDto {
    private Long employeeID;
    private String fullName;
    private String phone;
    private String email;
    private Double hourlyRate;
    private String note;

    public EmployeeDto(Long employeeID, String fullName, String phone, String email, Double hourlyRate, String note) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.hourlyRate = hourlyRate;
        this.note = note;
    }

    // Getters và Setters
    public Long getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Double getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
