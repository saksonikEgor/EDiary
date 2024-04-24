package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "classes_timetable")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LessonSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classes_timetable_id")
    private Integer lessonScheduleId;

    @Column(name = "lesson_date", nullable = false)
    private OffsetDateTime lessonDateTime;

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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        java.lang.Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        java.lang.Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LessonSchedule that = (LessonSchedule) o;
        return getLessonScheduleId() != null && Objects.equals(getLessonScheduleId(), that.getLessonScheduleId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
