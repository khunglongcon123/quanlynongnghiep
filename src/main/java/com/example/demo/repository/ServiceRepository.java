package com.example.demo.repository;

import com.example.demo.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s FROM Service s WHERE s.season.seasonID = :seasonId")
    List<Service> findBySeason(@Param("seasonId") Long seasonId);
    @Query("SELECT s FROM Service s WHERE s.season.seasonID = :seasonID AND s.dateStart <= :now AND s.dateStart >= :fourHoursAgo")
    List<Service> findOngoingServicesBySeasonId(@Param("seasonID") Long seasonID,
                                                @Param("now") LocalDateTime now,
                                                @Param("fourHoursAgo") LocalDateTime fourHoursAgo);
    List<Service> findByDateStartBetween(LocalDateTime start, LocalDateTime end);
}
