package com.example.job_management;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "category_group")
@Getter
@Setter
public class CategoryGroup {

    @Id
    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "categoryGroup")
    private List<Category> categories;
}
