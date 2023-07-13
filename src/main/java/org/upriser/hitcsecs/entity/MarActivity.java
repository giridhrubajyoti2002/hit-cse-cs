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
@Table(name = "mar_activity_list")
public class MarActivity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String activityId;

    @Column(unique = true, nullable = false)
    private String activityName;
    @Column(nullable = false)
    private Integer activityPoints;
    @Column(nullable = false)
    private Integer activityMaxPoints;
}
