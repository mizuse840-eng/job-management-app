package com.example.job_management;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        List<Job> jobs = jobService.getJobs(keyword);
        model.addAttribute("jobs", jobs);

        List<Status> statuses = jobService.getStatuses();
        model.addAttribute("statuses", statuses);

        Map<String, String> statusClassMap = Map.of(
                "NOT_STARTED", "text-bg-secondary", // グレー
                "APPLIED", "text-bg-primary", // 青
                "INTERVIEW", "text-bg-success", // 緑
                "REJECTED", "text-bg-dark", // 黒
                "OFFER", "text-bg-danger" // 赤
        );
        model.addAttribute("statusClassMap", statusClassMap);

        return "index";
    }

    @PostMapping("/add")
    public String addJob(@RequestParam String companyName,
            @RequestParam String status,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
            @RequestParam(required = false) String memo) {

        jobService.addJob(companyName, status, deadline, memo);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteJob(@RequestParam Long id) {
        jobService.deleteJob(id);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateJob(@RequestParam Long id,
            @RequestParam String status,
            @RequestParam(required = false) String memo) {

        jobService.updateJob(id, status, memo);
        return "redirect:/";
    }
}