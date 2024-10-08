package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "student_distribution")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDistribution {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "student_distribution_id")
    private UUID studentDistributionId;

//    @OneToOne(mappedBy = "studentDistribution")
    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private User student;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private Class clazz;

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
        StudentDistribution that = (StudentDistribution) o;
        return getStudentDistributionId() != null && Objects.equals(getStudentDistributionId(), that.getStudentDistributionId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}