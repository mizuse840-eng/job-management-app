package com.example.job_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // 検索と一覧取得の作業
    public List<Job> getJobs(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return jobRepository.findByCompanyNameContainingOrderByDeadlineAsc(keyword);
        } else {
            return jobRepository.findAllByOrderByDeadlineAsc();
        }
    }

    public void addJob(String companyName, String status, LocalDate deadline) {
        if (companyName == null || companyName.trim().isEmpty()) {
            return;
        }
        Job job = new Job();
        job.setCompanyName(companyName);

        job.setStatus(JobStatus.valueOf(status));

        job.setDeadline(deadline);
        jobRepository.save(job);
    }

    // 削除の作業
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // 更新の作業
    public void updateJob(Long id, String status) {
        Job job = jobRepository.findById(id).orElseThrow();

        job.setStatus(JobStatus.valueOf(status));

        jobRepository.save(job);
    }

    // データ追加の作業（引数とsetMemoを追加）
    public void addJob(String companyName, String status, LocalDate deadline, String memo) {
        if (companyName == null || companyName.trim().isEmpty()) {
            return;
        }
        Job job = new Job();
        job.setCompanyName(companyName);
        job.setStatus(JobStatus.valueOf(status));
        job.setDeadline(deadline);
        job.setMemo(memo);
        jobRepository.save(job);
    }

    // 更新の作業（引数とsetMemoを追加）
    public void updateJob(Long id, String status, String memo) {
        Job job = jobRepository.findById(id).orElseThrow();
        job.setStatus(JobStatus.valueOf(status));
        job.setMemo(memo);
        jobRepository.save(job);
    }
}