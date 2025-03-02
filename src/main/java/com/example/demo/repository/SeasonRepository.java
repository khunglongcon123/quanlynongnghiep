package com.example.demo.repository;

import com.example.demo.model.LandPlot;
import com.example.demo.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    List<Season> findByLandPlot(LandPlot landPlot);
    @Query("SELECT s FROM Season s WHERE s.landPlot.landPlotID = :landPlotID AND s.startDate <= CURRENT_DATE AND s.endDate >= CURRENT_DATE")
    Optional<Season> findCurrentSeasonByLandPlot(Long landPlotID);
}

