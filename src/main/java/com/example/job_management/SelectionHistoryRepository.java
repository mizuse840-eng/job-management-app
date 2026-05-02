package com.example.job_management;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionHistoryRepository extends JpaRepository<SelectionHistory, Long> {
    List<SelectionHistory> findByCompanyOrderByEventDateAsc(Company company);
}
