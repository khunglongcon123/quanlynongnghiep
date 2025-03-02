package com.example.demo.controller;


import com.example.demo.DTO.EmployeeDto;
import com.example.demo.DTO.MonthlyScheduleDto;
import com.example.demo.DTO.ServiceDto1;
import com.example.demo.DTO.WeeklyScheduleDto;
import com.example.demo.model.LandPlot;
import com.example.demo.model.Season;
import com.example.demo.model.Service;
import com.example.demo.model.WorkAssignment;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ScheduleController {

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * Endpoint lấy lịch theo tuần.
     * Yêu cầu: truyền tham số week theo định dạng "YYYY-Wxx" (ví dụ: "2025-W09")
     */
    @GetMapping("/weekly-schedule")
    public ResponseEntity<List<WeeklyScheduleDto>> getWeeklySchedule(@RequestParam("week") String week) {
        LocalDate monday;
        try {
            // Sử dụng YYYY thay vì yyyy để parse ISO week date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-'W'ww-e");

            // "2025-W09-1" => parse thành LocalDate
            TemporalAccessor ta = formatter.parse(week);
            LocalDate date = LocalDate.from(ta);

            // Nếu muốn luôn lấy Thứ Hai của tuần, đặt DAY_OF_WEEK = 1
            monday = date.with(ChronoField.DAY_OF_WEEK, 1);
        } catch (Exception e) {
            // Nếu parse thất bại => 400
            return ResponseEntity.badRequest().build();
        }

        LocalDateTime startDateTime = monday.atStartOfDay();
        LocalDateTime endDateTime = monday.plusDays(6).atTime(23, 59, 59);

        List<Service> services = serviceRepository.findByDateStartBetween(startDateTime, endDateTime);

        Map<LandPlot, List<Service>> grouped = services.stream()
                .collect(Collectors.groupingBy(s -> s.getSeason().getLandPlot()));

        List<WeeklyScheduleDto> result = new ArrayList<>();
        for (Map.Entry<LandPlot, List<Service>> entry : grouped.entrySet()) {
            LandPlot lp = entry.getKey();
            List<ServiceDto1> serviceDtos = entry.getValue().stream().map(s -> {
                List<EmployeeDto> employeeDtos = s.getWorkAssignments().stream()
                        .filter(wa -> wa.getEmployee() != null)
                        .map(wa -> new EmployeeDto(
                                wa.getEmployee().getEmployeeID(),
                                wa.getEmployee().getFullName(),
                                wa.getEmployee().getPhone(),
                                wa.getEmployee().getEmail(),
                                wa.getEmployee().getHourlyRate(),
                                wa.getEmployee().getNote()
                        ))
                        .collect(Collectors.toList());

                return new ServiceDto1(
                        s.getServiceID(),
                        s.getServiceType(),
                        s.getDescription(),
                        s.getDateStart(),
                        s.getEndTime(),
                        employeeDtos
                );
            }).collect(Collectors.toList());

            result.add(new WeeklyScheduleDto(lp.getLandPlotID(), lp.getName(), serviceDtos));
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint lấy lịch theo tháng.
     * Yêu cầu: truyền tham số month theo định dạng "YYYY-MM" (ví dụ: "2025-02")
     */
    @GetMapping("/monthly-schedule")
    public ResponseEntity<List<MonthlyScheduleDto>> getMonthlySchedule(@RequestParam("month") String month) {
        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(month);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        // Truy vấn các Service có dateStart nằm trong khoảng thời gian của tháng
        List<Service> services = serviceRepository.findByDateStartBetween(startDateTime, endDateTime);

        // Nhóm các Service theo Season
        Map<Season, List<Service>> grouped = services.stream()
                .collect(Collectors.groupingBy(Service::getSeason));

        List<MonthlyScheduleDto> result = new ArrayList<>();
        for (Map.Entry<Season, List<Service>> entry : grouped.entrySet()) {
            Season season = entry.getKey();
            List<ServiceDto1> serviceDtos = entry.getValue().stream().map(s -> {
                List<EmployeeDto> employeeDtos = s.getWorkAssignments().stream()
                        .filter(wa -> wa.getEmployee() != null)
                        .map(wa -> new EmployeeDto(
                                wa.getEmployee().getEmployeeID(),
                                wa.getEmployee().getFullName(),
                                wa.getEmployee().getPhone(),
                                wa.getEmployee().getEmail(),
                                wa.getEmployee().getHourlyRate(),
                                wa.getEmployee().getNote()
                        )).collect(Collectors.toList());
                return new ServiceDto1(
                        s.getServiceID(),
                        s.getServiceType(),
                        s.getDescription(),
                        s.getDateStart(),
                        s.getEndTime(),
                        employeeDtos
                );
            }).collect(Collectors.toList());
            result.add(new MonthlyScheduleDto(
                    season.getSeasonID(),
                    season.getLandPlot().getName(), // Lấy thông tin ô đất từ Season
                    season.getCrop(),
                    season.getTenCay(),
                    season.getStartDate(),
                    season.getEndDate(),
                    serviceDtos
            ));
        }
        return ResponseEntity.ok(result);
    }
}

