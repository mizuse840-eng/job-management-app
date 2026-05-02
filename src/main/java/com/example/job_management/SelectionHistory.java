package com.example.job_management;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "selection_history")
@Getter
@Setter
public class SelectionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "status_category_id", nullable = false)
    private Category status;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "detail_memo", columnDefinition = "TEXT")
    private String detailMemo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
