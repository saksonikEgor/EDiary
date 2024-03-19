package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "classroom_teacher_id", referencedColumnName = "user_id")
    private User classroomTeacher;

    @OneToOne(mappedBy = "clazz")
    private ClassList classList;

    @OneToMany(mappedBy = "clazz")
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "clazz")
    private List<LessonSchedule> lessonSchedules;
}
