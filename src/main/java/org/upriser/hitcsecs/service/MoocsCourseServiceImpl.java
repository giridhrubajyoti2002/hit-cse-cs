package org.upriser.hitcsecs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upriser.hitcsecs.entity.MoocsCourse;
import org.upriser.hitcsecs.repository.MoocsCourseRepository;

import java.util.List;

@Service
public class MoocsCourseServiceImpl implements MoocsCourseService{

    private final MoocsCourseRepository moocsCourseRepository;
    @Autowired
    public MoocsCourseServiceImpl(MoocsCourseRepository moocsCourseRepository) {
        this.moocsCourseRepository = moocsCourseRepository;
    }

    @Override
    public MoocsCourse addCourse(MoocsCourse moocsCourse) {
        return moocsCourseRepository.save(moocsCourse);
    }

    @Override
    public List<MoocsCourse> fetchAllMoocsCourses() {
        return moocsCourseRepository.findAll();
    }
}
