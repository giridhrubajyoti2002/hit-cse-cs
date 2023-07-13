package org.upriser.hitcsecs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.upriser.hitcsecs.enums.YearType;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "students"
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "unique_university_roll",
//                        columnNames = "universityRoll"
//                ),
//                @UniqueConstraint(
//                        name = "unique_class_roll",
//                        columnNames = "classRoll"
//                ),
//                @UniqueConstraint(
//                        name = "unique_university_registration",
//                        columnNames = "universityRegistration"
//                )
//        }
)
public class Student {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String studentId;

    private String firstName;
    private String lastName;

    @Column(length = 11, unique = true, nullable = false)
    private String universityRoll;

    @Column(length = 15, unique = true, nullable = false)
    private String universityRegistration;

    @Column(length = 16, unique = true, nullable = false)
    private String classRoll;

    @Enumerated(EnumType.STRING)
    private YearType currentYear;

    @OneToMany(
            mappedBy = "student",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Certificate> certificates;
}
