package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "classes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "class_id")
    private UUID classId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "classroom_teacher_id", referencedColumnName = "user_id")
    private User classroomTeacher;

    @OneToMany(mappedBy = "clazz")
    @ToString.Exclude
    private List<StudentDistribution> studentDistributions;

    @OneToMany(mappedBy = "clazz")
    @ToString.Exclude
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "clazz")
    @ToString.Exclude
    private List<LessonSchedule> lessonSchedules;

    @ManyToMany(mappedBy = "classesForTeacher")
    @ToString.Exclude
    private List<User> teachers;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "subjects_classes",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

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
        Class aClass = (Class) o;
        return getClassId() != null && Objects.equals(getClassId(), aClass.getClassId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
