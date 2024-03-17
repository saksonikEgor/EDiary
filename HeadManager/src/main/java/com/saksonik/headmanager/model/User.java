package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(mappedBy = "roles_users")
    List<UserRole> userRoles;

    @ManyToMany
    @JoinTable(
            name = "parents_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private List<User> parents;

    @ManyToMany(mappedBy = "parents")
    private List<User> children;

    @ManyToMany
    @JoinTable(
            name = "teaching_staff",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @OneToMany(mappedBy = "classes")
    List<Class> classesForTeacher;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "student_id")
    private ClassList classList;

    @OneToMany(mappedBy = "teacher")
    List<LessonSchedule> lessonSchedules;

    @OneToMany(mappedBy = "student")
    List<Mark> marksOfStudent;

    @OneToMany(mappedBy = "teacher")
    List<Mark> marksOfTeacher;
}
