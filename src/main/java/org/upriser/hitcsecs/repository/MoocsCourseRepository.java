package org.upriser.hitcsecs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upriser.hitcsecs.entity.MoocsCourse;

@Repository
public interface MoocsCourseRepository extends JpaRepository<MoocsCourse,String> {
}
