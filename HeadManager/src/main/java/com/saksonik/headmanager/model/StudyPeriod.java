package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "study_periods")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class StudyPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_period_id")
    private Integer studyPeriodId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "studyPeriod")
    @ToString.Exclude
    private List<Mark> marks;

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
        StudyPeriod that = (StudyPeriod) o;
        return getStudyPeriodId() != null && Objects.equals(getStudyPeriodId(), that.getStudyPeriodId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
