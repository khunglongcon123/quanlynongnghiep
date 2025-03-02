package com.example.demo.controller;

import com.example.demo.model.LandPlot;
import com.example.demo.model.Season;
import com.example.demo.model.Service;
import com.example.demo.model.WorkAssignment;
import com.example.demo.repository.LandPlotRepository;
import com.example.demo.repository.SeasonRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.WorkAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/land-plots")
public class LandPlotController {

    @Autowired
    LandPlotRepository landPlotRepository;
    @Autowired
    SeasonRepository seasonRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    WorkAssignmentRepository workAssignmentRepository;
    @GetMapping("/{landPlotID}/ongoing-services")
    public ResponseEntity<?> getOngoingServices(@PathVariable Long landPlotID) {
        Optional<Season> currentSeason = seasonRepository.findCurrentSeasonByLandPlot(landPlotID);
        if (currentSeason.isEmpty()) {
            return ResponseEntity.ok("Không có mùa vụ nào đang diễn ra tại ô đất này.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fourHoursAgo = now.minusHours(4);
        List<Service> ongoingServices = serviceRepository.findOngoingServicesBySeasonId(currentSeason.get().getSeasonID(), now, fourHoursAgo);
        if (ongoingServices.isEmpty()) {
            return ResponseEntity.ok("Không có dịch vụ nào đang thực hiện tại ô đất này.");
        }

        return ResponseEntity.ok(ongoingServices);
    }

    @GetMapping("/service/{serviceID}/workers")
    public ResponseEntity<?> getServiceWorkers(@PathVariable Long serviceID) {
        List<WorkAssignment> workers = workAssignmentRepository.findActiveWorkersByService(serviceID);
        if (workers.isEmpty()) {
            return ResponseEntity.ok("Không có người làm nào đang thực hiện dịch vụ này.");
        }

        return ResponseEntity.ok(workers);
    }

    @GetMapping
    public List<LandPlot> getAll() {
        return landPlotRepository.findAll();
    }

    @GetMapping("/land-plots")
    public List<Map<String, Object>> getAllLandPlots() {
        List<LandPlot> landPlots = landPlotRepository.findAll();

        return landPlots.stream().map(landPlot -> {
            Map<String, Object> result = new HashMap<>();
            result.put("landPlotID", landPlot.getLandPlotID());
            result.put("name", landPlot.getName());
            result.put("coordinates", landPlot.getCoordinates());

            // 🔥 Tách tọa độ
            if (landPlot.getCoordinates() != null && landPlot.getCoordinates().contains(",")) {
                String[] parts = landPlot.getCoordinates().split(",");
                try {
                    result.put("latitude", Double.parseDouble(parts[0].trim()));
                    result.put("longitude", Double.parseDouble(parts[1].trim()));
                } catch (NumberFormatException e) {
                    result.put("latitude", null);
                    result.put("longitude", null);
                }
            } else {
                result.put("latitude", null);
                result.put("longitude", null);
            }

            return result;
        }).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LandPlot> getById(@PathVariable Long id) {
        return landPlotRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LandPlot create(@RequestBody LandPlot landPlot) {
        return landPlotRepository.save(landPlot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandPlot> update(@PathVariable Long id, @RequestBody LandPlot landPlot) {
        return landPlotRepository.findById(id)
                .map(ex -> {
                    landPlot.setLandPlotID(ex.getLandPlotID());
                    return ResponseEntity.ok(landPlotRepository.save(landPlot));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        landPlotRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
