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

    // Repositoryの代わりに、さっき作ったService（作業員）を呼び出す
    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        // 作業員にデータを取ってこさせる
        List<Job> jobs = jobService.getJobs(keyword);
        model.addAttribute("jobs", jobs);
        return "index";
    }

    @PostMapping("/add")
    public String addJob(@RequestParam String companyName,
            @RequestParam String status,
            @RequestParam LocalDate deadline) {
        // 作業員に追加を任せる
        jobService.addJob(companyName, status, deadline);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteJob(@RequestParam Long id) {
        // 作業員に削除を任せる
        jobService.deleteJob(id);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateJob(@RequestParam Long id, @RequestParam String status) {
        // 作業員に更新を任せる
        jobService.updateJob(id, status);
        return "redirect:/";
    }
}