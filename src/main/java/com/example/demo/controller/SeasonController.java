package com.example.demo.controller;

import com.example.demo.DTO.SeasonDTO;
import com.example.demo.model.LandPlot;
import com.example.demo.model.Season;
import com.example.demo.repository.LandPlotRepository;
import com.example.demo.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/seasons")
public class SeasonController {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private LandPlotRepository landPlotRepository;

//    @GetMapping
//    public List<Season> getAll() {
//        return seasonRepository.findAll();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdLandpot(@PathVariable Long id) {
        LandPlot landPlot = landPlotRepository.findById(id).get();
        List<Season> list = seasonRepository.findByLandPlot(landPlot);
        if (list == null || list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mùa vụ nào cho ô đất ID: " + id);
        }
        return ResponseEntity.ok(list);
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<Season> getById(@PathVariable Long id) {
//        return seasonRepository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PostMapping
    public Season create(@RequestBody SeasonDTO season) {
        LandPlot landPlot = landPlotRepository.findById(season.getLandPlotId()).get();
        Season season1 = new Season();
        season1.setCrop(season.getCrop());
        season1.setEndDate(season.getEndDate());
        season1.setLandPlot(landPlot);
        season1.setStartDate(season.getStartDate());
        season1.setNote(season.getNote());
        season1.setTenCay(season.getTenCay());
        return seasonRepository.save(season1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Season> update(@PathVariable Long id, @RequestBody Season season) {
        return seasonRepository.findById(id)
                .map(existingSeason -> {
                    season.setSeasonID(existingSeason.getSeasonID());
                    return ResponseEntity.ok(seasonRepository.save(season));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seasonRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
