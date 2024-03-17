package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "meetings")
@Data
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Integer meetingId;

    @Column(name = "meeting_date", nullable = false)
    private OffsetDateTime meetingDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private Class clazz;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "classroom_id")
    private Classroom classroom;
}
