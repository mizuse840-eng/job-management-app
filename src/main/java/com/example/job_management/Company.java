package com.example.job_management;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String industry;

    private String url;

    @Column(columnDefinition = "TEXT")
    private String memo;

    private String jobType;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @jakarta.persistence.OrderBy("eventDate DESC")
    private List<SelectionHistory> selectionHistories;
}
