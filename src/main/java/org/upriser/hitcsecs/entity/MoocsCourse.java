package org.upriser.hitcsecs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "moocs_course_list")
public class MoocsCourse {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String courseId;

    @Column(unique = true, nullable = false)
    private String courseName;
    private String courseProvider;
    private Integer courseDuration;
    @Column(nullable = false)
    private Integer courseCredit;
}
