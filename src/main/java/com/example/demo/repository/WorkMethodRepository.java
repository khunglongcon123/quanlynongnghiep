package com.example.demo.repository;

import com.example.demo.model.WorkMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkMethodRepository extends JpaRepository<WorkMethod, Long> {
}
