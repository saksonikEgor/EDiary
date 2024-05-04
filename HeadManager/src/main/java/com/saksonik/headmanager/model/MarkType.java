package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "mark_types")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MarkType {
    @Id
    @Column(name = "mark_type_id")
    private UUID markTypeId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "weight")
    private Integer weight;

    @OneToMany(mappedBy = "markType")
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
        MarkType markType = (MarkType) o;
        return getMarkTypeId() != null && Objects.equals(getMarkTypeId(), markType.getMarkTypeId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
