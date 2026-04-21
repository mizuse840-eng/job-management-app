package com.example.job_management;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        List<Job> jobs = jobService.getJobs(keyword);
        model.addAttribute("jobs", jobs);
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