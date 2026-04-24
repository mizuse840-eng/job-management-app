package com.example.job_management;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "statuses") // DB上のテーブル名
@Getter
@Setter
public class Status {
    @Id
    private String code; // 'APPLIED', 'INTERVIEW' などのコード（主キー）

    private String name; // '応募済み', '面接待ち' などの表示名

    private int sortOrder; // 表示させる順番
}