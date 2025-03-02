package com.example.demo.controller;

import com.example.demo.DTO.ServiceDTO;
import com.example.demo.model.Season;
import com.example.demo.model.Service;
import com.example.demo.repository.SeasonRepository;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private SeasonRepository seasonRepository;

    @GetMapping
    public List<Service> getAll() {
        return serviceRepository.findAll();
    }

    @GetMapping("/bySeasonId/{id}")
    public List<Service> getByIdSeason(@PathVariable Long id) {
        List<Service> list = serviceRepository.findBySeason(id);
        return list;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Service> getById(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ServiceDTO serviceDTO) {
        // Tìm season theo ID
        Season season = seasonRepository.findById(serviceDTO.getSeasonId())
                .orElseThrow(() -> new RuntimeException("Season không tồn tại"));

        LocalDateTime newStartTime = serviceDTO.getDateStart();
        LocalDateTime newEndTime = newStartTime.plusHours(4); // Mỗi dịch vụ kéo dài tối đa 4 tiếng

        // Lấy danh sách dịch vụ cùng Season
        List<Service> existingServices = serviceRepository.findBySeason(serviceDTO.getSeasonId());

        // Kiểm tra xem có dịch vụ nào bị trùng thời gian không
        for (Service existingService : existingServices) {
            LocalDateTime existingStartTime = existingService.getDateStart();
            LocalDateTime existingEndTime = existingService.getEndTime();

            // Nếu khoảng thời gian mới bị chồng lên một dịch vụ đã có
            if ((newStartTime.isBefore(existingEndTime) && newEndTime.isAfter(existingStartTime))) {
                return ResponseEntity.badRequest().body("Lỗi: Đã có dịch vụ khác trong khoảng thời gian này!");
            }
        }

        // Nếu hợp lệ, tạo dịch vụ mới
        Service service = new Service();
        service.setCost(serviceDTO.getCost());
        service.setServiceType(serviceDTO.getServiceType());
        service.setDescription(serviceDTO.getDescription());
        service.setSeason(season);
        service.setDateStart(newStartTime);

        Service savedService = serviceRepository.save(service);
        return ResponseEntity.ok(savedService);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Service> update(@PathVariable Long id, @RequestBody Service service) {
        return serviceRepository.findById(id)
                .map(existingService -> {
                    service.setServiceID(existingService.getServiceID());
                    return ResponseEntity.ok(serviceRepository.save(service));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
