package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "classrooms")
@Data
@NoArgsConstructor
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classroom_id")
    private Integer classroomId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "classroom")
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "classroom")
    private List<LessonSchedule> lessonSchedules;
}
