package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "mark_types")
@Data
@NoArgsConstructor
public class MarkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_type_id")
    private Integer markTypeId;

    @Column(name = "type", nullable = false)
    String type;

    @OneToMany(mappedBy = "markType")
    List<Mark> marks;
}
