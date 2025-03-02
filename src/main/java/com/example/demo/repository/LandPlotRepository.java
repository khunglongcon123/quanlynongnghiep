package com.example.demo.repository;

import com.example.demo.model.LandPlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandPlotRepository extends JpaRepository<LandPlot, Long> {
}
