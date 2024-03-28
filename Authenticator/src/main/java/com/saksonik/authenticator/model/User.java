package com.saksonik.authenticator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "login", nullable = false)
    private String login;

//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "surname", nullable = false)
//    private String surname;
//
//    @Column(name = "patronymic")
//    private String patronymic;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

//    @Column(name = "phone_number", nullable = false)
//    private String phoneNumber;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private OffsetDateTime registrationDate;

//    @Column(name = "birth_date", nullable = false)
//    private LocalDate birthDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
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
