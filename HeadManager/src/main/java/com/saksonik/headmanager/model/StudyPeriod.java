package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "study_periods")
@Data
@NoArgsConstructor
public class StudyPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_period_id")
    private Integer studyPeriodId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "studyPeriod")
    private List<Mark> marks;
}
