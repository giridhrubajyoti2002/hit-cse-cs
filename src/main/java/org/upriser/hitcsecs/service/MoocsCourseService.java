package org.upriser.hitcsecs.service;

import org.upriser.hitcsecs.entity.MoocsCourse;

import java.util.List;

public interface MoocsCourseService {
    MoocsCourse addCourse(MoocsCourse moocsCourse);

    List<MoocsCourse> fetchAllMoocsCourses();
}
