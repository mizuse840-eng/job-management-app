package com.example.job_management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByOrderByDeadlineAsc();

    List<Job> findByCompanyNameContainingOrderByDeadlineAsc(String companyName);
}