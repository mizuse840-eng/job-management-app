package com.example.job_management;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        List<Job> jobs;
        if (keyword != null && !keyword.isEmpty()) {
            // 検索ワードがあれば絞り込み
            jobs = jobRepository.findByCompanyNameContainingOrderByDeadlineAsc(keyword);
        } else {
            // なければ全件（日付順）
            jobs = jobRepository.findAllByOrderByDeadlineAsc();
        }
        model.addAttribute("jobs", jobs);
        return "index";
    }

    // HelloController.java の addJob メソッドを書き換え
    @PostMapping("/add")
    public String addJob(@RequestParam String companyName,
            @RequestParam String status,
            @RequestParam LocalDate deadline) {
        if (companyName == null || companyName.trim().isEmpty()) {
            return "redirect:/";
        }
        Job job = new Job();
        job.setCompanyName(companyName);
        job.setStatus(status);
        job.setDeadline(deadline); // 日付をセット
        jobRepository.save(job);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteJob(@RequestParam Long id) {
        jobRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateJob(@RequestParam Long id, @RequestParam String status) {
        Job job = jobRepository.findById(id).orElseThrow();
        job.setStatus(status);
        jobRepository.save(job);
        return "redirect:/";
    }
}