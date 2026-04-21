package com.example.job_management;

import jakarta.persistence.*; // EntityやIdなどをまとめてインポート
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    // ここを修正：StringからJobStatus(Enum)に変更
    // @Enumerated(EnumType.STRING) をつけることで、DBには文字列として保存される
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    // ゲッターとセッターも JobStatus型 に書き換える
    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}