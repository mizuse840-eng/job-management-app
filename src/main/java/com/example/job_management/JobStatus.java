package com.example.job_management;

public enum JobStatus {
    NOT_STARTED("未着手"),
    IN_PROGRESS("応募準備中"),
    APPLIED("ES提出済み"),
    INTERVIEW("面接待ち"),
    OFFER("内定"),
    REJECTED("お見送り");

    private final String displayName;

    JobStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}