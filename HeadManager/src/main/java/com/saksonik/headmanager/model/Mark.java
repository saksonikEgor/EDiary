package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "marks")
@Data
@NoArgsConstructor
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_id")
    private Integer markId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    @Column(name = "last_modified_at", nullable = false)
    private OffsetDateTime lastModifiedAt = OffsetDateTime.now(ZoneOffset.UTC);

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "work_type_id", referencedColumnName = "work_type_id")
    private WorkType workType;

    @ManyToOne
    @JoinColumn(name = "study_period_id", referencedColumnName = "study_period_id")
    private StudyPeriod studyPeriod;

    @ManyToOne
    @JoinColumn(name = "mark_type_id", referencedColumnName = "mark_type_id")
    private MarkType markType;
}
