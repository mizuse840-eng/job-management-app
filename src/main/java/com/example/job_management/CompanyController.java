package com.example.job_management;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        List<Company> companies = companyService.getCompanies(keyword);
        model.addAttribute("companies", companies);

        List<Category> selectionStatuses = companyService.getSelectionStatuses();
        model.addAttribute("selectionStatuses", selectionStatuses);

        Map<String, Long> statusSummary = companies.stream()
                .filter(c -> c.getSelectionHistories() != null && !c.getSelectionHistories().isEmpty())
                .collect(Collectors.groupingBy(
                        c -> c.getSelectionHistories().get(0).getStatus().getCategoryName(),
                        Collectors.counting()));
        model.addAttribute("statusSummary", statusSummary);

        return "index";
    }

    @GetMapping("/company/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.getCompanyById(id));
        model.addAttribute("selectionStatuses", companyService.getSelectionStatuses());
        return "detail";
    }

    @GetMapping("/company/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.getCompanyById(id));
        return "edit";
    }

    @PostMapping("/company/{id}/edit")
    public String updateCompany(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String memo) {

        companyService.updateCompany(id, name, industry, url, jobType, memo);
        return "redirect:/company/" + id;
    }

    @PostMapping("/company/add")
    public String addCompany(
            @RequestParam String name,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String memo,
            @RequestParam(required = false) Long statusCategoryId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate) {

        companyService.addCompany(name, industry, url, jobType, memo, statusCategoryId, eventDate);
        return "redirect:/";
    }

    @PostMapping("/company/delete")
    public String deleteCompany(@RequestParam Long id) {
        companyService.deleteCompany(id);
        return "redirect:/";
    }

    @PostMapping("/history/add")
    public String addHistory(
            @RequestParam Long companyId,
            @RequestParam Long statusCategoryId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate,
            @RequestParam(required = false) String detailMemo) {

        companyService.addSelectionHistory(companyId, statusCategoryId, eventDate, detailMemo);
        return "redirect:/company/" + companyId;
    }

    @PostMapping("/history/delete")
    public String deleteHistory(@RequestParam Long id, @RequestParam Long companyId) {
        companyService.deleteSelectionHistory(id);
        return "redirect:/company/" + companyId;
    }
}
