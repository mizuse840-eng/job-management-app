package com.example.job_management;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @ManyToOne
    @JoinColumn(name = "status_code", referencedColumnName = "code")
    private Status status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @Column(columnDefinition = "TEXT")
    private String memo;

}
