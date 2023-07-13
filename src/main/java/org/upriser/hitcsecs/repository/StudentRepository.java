package org.upriser.hitcsecs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upriser.hitcsecs.entity.Student;
import org.upriser.hitcsecs.enums.YearType;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {

    List<Student> findByCurrentYear(YearType currentYear);

    void deleteAllByCurrentYear(YearType currentYear);
}
