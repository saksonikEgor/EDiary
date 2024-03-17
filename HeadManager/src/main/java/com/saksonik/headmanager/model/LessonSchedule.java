package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "classes_timetable")
@Data
@NoArgsConstructor
public class LessonSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classes_timetable_id")
    private Integer lessonScheduleId;

    @Column(name = "lesson_date", nullable = false)
    private OffsetDateTime lessonDate;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "classroom_id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private Class clazz;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "lesson_number_id", referencedColumnName = "lesson_number_id")
    private ScheduledCall scheduledCall;
}
