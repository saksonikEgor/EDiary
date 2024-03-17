package com.saksonik.headmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "class_lists")
@Data
@NoArgsConstructor
public class ClassList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_list_id")
    private Integer classListId;

    @OneToMany(mappedBy = "classList")
    List<User> students;

    @OneToOne()
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private Class clazz;


}
