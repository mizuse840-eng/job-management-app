package com.example.job_management;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
    List<Status> findAllByOrderBySortOrderAsc();
}
