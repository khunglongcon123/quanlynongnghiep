package com.example.demo.DTO;

import java.time.LocalDate;

public class SeasonDTO {
    public Long getLandPlotId() {
        return landPlotId;
    }

    public void setLandPlotId(Long landPlotId) {
        this.landPlotId = landPlotId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private Long landPlotId;

    private String crop;
    private String tenCay;
    private LocalDate startDate;
    private LocalDate endDate;
    private String note;
}
