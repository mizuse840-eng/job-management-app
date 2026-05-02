package com.example.job_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryGroupRepository categoryGroupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (categoryGroupRepository.count() > 0) {
            return;
        }

        CategoryGroup statusGroup = new CategoryGroup();
        statusGroup.setGroupCode("01");
        statusGroup.setGroupName("選考ステータス");
        categoryGroupRepository.save(statusGroup);

        String[][] statuses = {
            {"01", "ES提出済み",   "1"},
            {"02", "書類選考中",   "2"},
            {"03", "1次面接",      "3"},
            {"04", "最終面接",     "4"},
            {"05", "内定",         "5"},
            {"06", "お見送り",     "6"},
        };
        for (String[] s : statuses) {
            Category c = new Category();
            c.setCategoryGroup(statusGroup);
            c.setCategoryCode(s[0]);
            c.setCategoryName(s[1]);
            c.setSortOrder(Integer.parseInt(s[2]));
            categoryRepository.save(c);
        }
    }
}
