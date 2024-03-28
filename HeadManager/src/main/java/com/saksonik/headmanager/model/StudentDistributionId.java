package com.saksonik.headmanager.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDistributionId implements Serializable {
    private Integer studentId;
    private Integer classId;

//    @OneToOne(mappedBy = "studentDistribution")
//    private User student;
//
//    @ManyToOne
//    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
//    private Class clazz;
}
