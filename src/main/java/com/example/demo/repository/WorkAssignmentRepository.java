package com.example.demo.repository;

import com.example.demo.model.WorkAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkAssignmentRepository extends JpaRepository<WorkAssignment, Long> {
    List<WorkAssignment> findByService_ServiceID(Long serviceId);
    @Query("SELECT wa FROM WorkAssignment wa " +
            "JOIN FETCH wa.service s " +
            "JOIN FETCH s.season se " +
            "JOIN FETCH se.landPlot lp " +
            "WHERE wa.employee.employeeID = :employeeId")
    List<WorkAssignment> findByEmployeeId(@Param("employeeId") Long employeeId);
    @Query("SELECT wa FROM WorkAssignment wa WHERE wa.service.serviceID = :serviceID AND wa.status = 'Đang thực hiện'")
    List<WorkAssignment> findActiveWorkersByService(Long serviceID);
}
