package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "subject_id")
    private UUID subjectId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "subjects")
    @ToString.Exclude
    private List<User> teachers;

    @OneToMany(mappedBy = "subject")
    @ToString.Exclude
    private List<LessonSchedule> lessonSchedules;

    @OneToMany(mappedBy = "subject")
    @ToString.Exclude
    private List<Mark> marks;

    @ManyToMany(mappedBy = "subjects")
    @ToString.Exclude
    private List<Class> classes;

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
        Subject subject = (Subject) o;
        return getSubjectId() != null && Objects.equals(getSubjectId(), subject.getSubjectId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
