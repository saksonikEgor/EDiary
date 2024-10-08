package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Classroom {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "classroom_id")
    private UUID classroomId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "classroom")
    @ToString.Exclude
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "classroom")
    @ToString.Exclude
    private List<LessonSchedule> lessonSchedules;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        java.lang.Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        java.lang.Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Classroom classroom = (Classroom) o;
        return getClassroomId() != null && Objects.equals(getClassroomId(), classroom.getClassroomId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
