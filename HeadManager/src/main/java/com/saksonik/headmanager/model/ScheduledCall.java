package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "call_schedule")
@Data
@NoArgsConstructor
public class ScheduledCall {
    @Id
    @Column(name = "lesson_number_id")
    private Integer lessonNumber;

    @Column(name = "start_time", nullable = false)
    private LocalTime start;

    @Column(name = "end_time", nullable = false)
    private LocalTime end;

    @OneToMany(mappedBy = "scheduledCall")
    List<LessonSchedule> lessonSchedules;
}
