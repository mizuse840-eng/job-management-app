package com.example.job_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompanyService {

    private static final String SELECTION_STATUS_GROUP = "01";

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SelectionHistoryRepository selectionHistoryRepository;

    public List<Company> getCompanies(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return companyRepository.findByNameContainingOrderByNameAsc(keyword);
        }
        return companyRepository.findAllByOrderByNameAsc();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow();
    }

    public void addCompany(String name, String industry, String url, String jobType, String memo,
                           Long statusCategoryId, LocalDate eventDate) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        Company company = new Company();
        company.setName(name);
        company.setIndustry(industry);
        company.setUrl(url);
        company.setJobType(jobType);
        company.setMemo(memo);
        companyRepository.save(company);

        if (statusCategoryId != null && eventDate != null) {
            addSelectionHistory(company.getId(), statusCategoryId, eventDate, null);
        }
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public List<Category> getSelectionStatuses() {
        return categoryRepository.findByCategoryGroup_GroupCodeOrderBySortOrderAsc(SELECTION_STATUS_GROUP);
    }

    public void addSelectionHistory(Long companyId, Long statusCategoryId, LocalDate eventDate, String detailMemo) {
        Company company = companyRepository.findById(companyId).orElseThrow();
        Category status = categoryRepository.findById(statusCategoryId).orElseThrow();

        SelectionHistory history = new SelectionHistory();
        history.setCompany(company);
        history.setStatus(status);
        history.setEventDate(eventDate);
        history.setDetailMemo(detailMemo);
        history.setCreatedAt(LocalDateTime.now());

        selectionHistoryRepository.save(history);
    }

    public void deleteSelectionHistory(Long id) {
        selectionHistoryRepository.deleteById(id);
    }
}
