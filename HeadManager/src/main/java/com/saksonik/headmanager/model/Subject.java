package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "subjects")
    private List<User> teachers;

    @OneToMany(mappedBy = "subject")
    private List<LessonSchedule> lessonSchedules;

    @OneToMany(mappedBy = "subject")
    private List<Mark> marks;
}
