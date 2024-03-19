package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "work_types")
@Data
@NoArgsConstructor
public class WorkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_type_id")
    private Integer workTypeId;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "workType")
    private List<Mark> marks;
}
