package com.example.job_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getStatuses() {
        return statusRepository.findAllByOrderBySortOrderAsc();
    }

    // 検索と一覧取得の作業
    public List<Job> getJobs(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return jobRepository.findByCompanyNameContainingOrderByDeadlineAsc(keyword);
        } else {
            return jobRepository.findAllByOrderByDeadlineAsc();
        }
    }

    public void addJob(String companyName, String statusCode, LocalDate deadline) {
        if (companyName == null || companyName.trim().isEmpty()) {
            return;
        }
        Job job = new Job();
        job.setCompanyName(companyName);

        Status status = statusRepository.findById(statusCode).orElse(null);
        job.setStatus(status);

        job.setDeadline(deadline);
        jobRepository.save(job);
    }

    // 削除の作業
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // 更新の作業
    public void updateJob(Long id, String statusCode) {
        Job job = jobRepository.findById(id).orElseThrow();

        Status status = statusRepository.findById(statusCode).orElse(null);
        job.setStatus(status);

        jobRepository.save(job);
    }

    // データ追加の作業
    public void addJob(String companyName, String statusCode, LocalDate deadline, String memo) {
        if (companyName == null || companyName.trim().isEmpty()) {
            return;
        }
        Job job = new Job();
        job.setCompanyName(companyName);

        Status status = statusRepository.findById(statusCode).orElse(null);
        job.setStatus(status);

        job.setDeadline(deadline);
        job.setMemo(memo);
        jobRepository.save(job);
    }

    public void updateJob(Long id, String statusCode, String memo) {
        Job job = jobRepository.findById(id).orElseThrow();

        Status status = statusRepository.findById(statusCode).orElse(null);
        job.setStatus(status);

        job.setMemo(memo);
        jobRepository.save(job);
    }
}