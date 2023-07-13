package org.upriser.hitcsecs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.upriser.hitcsecs.enums.CertificateType;
import org.upriser.hitcsecs.enums.YearType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String certificateId;

    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String contentType;
    @Lob()
    @Column(length = 10*1024*1024, nullable = false)
    @JsonIgnore
    private byte[] data;
    private String downloadUrl;

    @ManyToOne(
            targetEntity = Student.class,
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    @JsonIgnore
    private Student student;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CertificateType certificateType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YearType yearType;


    private String activityOrCourseId;

    @Column(nullable = false)
    private boolean verified;


}
