package org.upriser.hitcsecs.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;
}
