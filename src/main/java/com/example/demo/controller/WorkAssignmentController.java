package com.example.demo.controller;

import com.example.demo.DTO.WorkAssignmentDTO;
import com.example.demo.DTO.WorkAssignmentResponseDTO;
import com.example.demo.model.*;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.WorkAssignmentRepository;
import com.example.demo.repository.WorkMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/work-assignments")
public class WorkAssignmentController {

    @Autowired
    private WorkAssignmentRepository workAssignmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private WorkMethodRepository workMethodRepository;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public WorkAssignment create(@RequestBody WorkAssignmentDTO workAssignmentDTO) {
        Service service = serviceRepository.findById(workAssignmentDTO.getServiceId()).orElseThrow();
        WorkMethod workMethod = workMethodRepository.findById(workAssignmentDTO.getWorkMethodId()).orElseThrow();
        Employee employee = null;

        if (workAssignmentDTO.getEmployeeId() != -1) {
            employee = employeeRepository.findById(workAssignmentDTO.getEmployeeId()).orElseThrow();
        }

        WorkAssignment workAssignment = new WorkAssignment();
        if (workMethod.getName().equals("Thuê người")) {
            workAssignment.setEmployee(employee);
        }
        workAssignment.setDetails(workAssignmentDTO.getDetails());
        workAssignment.setService(service);
        workAssignment.setStatus(workAssignmentDTO.getStatus());
        workAssignment.setWorkMethod(workMethod);

        WorkAssignment savedAssignment = workAssignmentRepository.save(workAssignment);

        // Gửi email thông báo nếu có nhân viên được phân công
        if (employee != null && workMethod.getName().equals("Thuê người")) {
            sendAssignmentEmail(employee, service);
        }

        return savedAssignment;
    }

    private void sendAssignmentEmail(Employee employee, Service service) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employee.getEmail());
        message.setSubject("Thông báo thanh toán cho nhân viên");

        Season season = service.getSeason();
        LandPlot landPlot = season.getLandPlot();

        String emailContent = "Xin chào " + employee.getFullName() + ",\n" +
                "THANH TOÁN TIỀN LÀM DỊCH VỤ " + ",\n\n" +
                "THÔNG TIN DỊCH VỤ " + ",\n" +
                "Phân công làm việc tại ô đất: " + landPlot.getName() + ",\n" +
                "Mùa vụ: " + season.getCrop() + " (" + season.getStartDate() + " - " + season.getEndDate() + "),\n" +
                "Dịch vụ: " + service.getServiceType() + "\n" +
                "Chi phí: 150000 VND" + "\n" +
                "Thời gian bắt đầu: " + service.getDateStart() + "\n\n" +
                "THÔNG TIN THANH TOÁN" + "\n" +
                "Số tài khoản: 4304205268590" + "\n" +
                "Số tiền: 150000 VND" + "\n" +
                "Ngày thanh toán: 26/02/2025 8:00 PM" + "\n" +
                "Vui lòng kiểm tra tài khoản và phản hồi nếu có vấn đề.\n\nTrân trọng.";

        message.setText(emailContent);
        mailSender.send(message);
    }

    @GetMapping
    public List<WorkAssignment> getAll(@RequestParam(required = false) Long serviceId) {
        if (serviceId != null) {
            return workAssignmentRepository.findByService_ServiceID(serviceId);
        }
        return workAssignmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkAssignment> getById(@PathVariable Long id) {
        return workAssignmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<WorkAssignmentResponseDTO>> getByEmployeeId(@PathVariable Long employeeId) {
        List<WorkAssignment> workAssignments = workAssignmentRepository.findByEmployeeId(employeeId);

        if (workAssignments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Chuyển danh sách WorkAssignment sang DTO
        List<WorkAssignmentResponseDTO> response = workAssignments.stream().map(work -> new WorkAssignmentResponseDTO(
                work.getAssignmentID(),
                work.getWorkMethod().getName(),
                work.getDetails(),
                work.getStatus(),
                work.getService().getServiceType(),
                work.getService().getSeason().getCrop(),
                work.getService().getSeason().getStartDate(),
                work.getService().getSeason().getEndDate(),
                work.getService().getSeason().getLandPlot().getName(),
                work.getService().getSeason().getLandPlot().getCoordinates(),
                work.getService().getDateStart()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


//    @PostMapping
//    public WorkAssignment create(@RequestBody WorkAssignmentDTO workAssignmentDTO) {
//        Service service = serviceRepository.findById(workAssignmentDTO.getServiceId()).get();
//        WorkMethod workMethod = workMethodRepository.findById(workAssignmentDTO.getWorkMethodId()).get();
//        Employee employee = null;
//        if(workAssignmentDTO.getEmployeeId() != -1){
//            employee = employeeRepository.findById(workAssignmentDTO.getEmployeeId()).get();
//        }
//        WorkAssignment w = new WorkAssignment();
//        if(workMethod.getName().equals("Thuê người"))
//            w.setEmployee(employee);
//        w.setDetails(workAssignmentDTO.getDetails());
//        w.setService(service);
//        w.setStatus(workAssignmentDTO.getStatus());
//        w.setWorkMethod(workMethod);
//        return workAssignmentRepository.save(w);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkAssignment> update(@PathVariable Long id, @RequestBody WorkAssignment workAssignment) {
        return workAssignmentRepository.findById(id)
                .map(existingWorkAssignment -> {
                    workAssignment.setAssignmentID(existingWorkAssignment.getAssignmentID());
                    return ResponseEntity.ok(workAssignmentRepository.save(workAssignment));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (workAssignmentRepository.existsById(id)) {
            workAssignmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
