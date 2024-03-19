package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<UserRole> userRoles;

    @ManyToMany
    @JoinTable(
            name = "parents_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    @ToString.Exclude
    private List<User> parents;

    @ManyToMany(mappedBy = "parents")
    @ToString.Exclude
    private List<User> children;

    @ManyToMany
    @JoinTable(
            name = "teaching_staff",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @ToString.Exclude
    private List<Subject> subjects;

    @OneToMany(mappedBy = "classroomTeacher")
    @ToString.Exclude
    private List<Class> classesForTeacher;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "student_id")
    private ClassList classList;

    @OneToMany(mappedBy = "teacher")
    @ToString.Exclude
    private List<LessonSchedule> lessonSchedules;

    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    private List<Mark> marksOfStudent;

    @OneToMany(mappedBy = "teacher")
    @ToString.Exclude
    private List<Mark> marksOfTeacher;

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
        User user = (User) o;
        return getUserId() != null && Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
