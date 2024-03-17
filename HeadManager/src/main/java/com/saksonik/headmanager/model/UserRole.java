package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles_users")
@Data
@NoArgsConstructor
public class UserRole {
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;
}
