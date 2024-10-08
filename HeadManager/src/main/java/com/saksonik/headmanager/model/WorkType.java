package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "work_types")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WorkType {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "work_type_id")
    private UUID workTypeId;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "workType")
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
        WorkType workType = (WorkType) o;
        return getWorkTypeId() != null && Objects.equals(getWorkTypeId(), workType.getWorkTypeId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
