package org.upriser.hitcsecs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upriser.hitcsecs.entity.MarActivity;
import org.upriser.hitcsecs.repository.MarActivityRepository;

import java.util.List;

@Service
public class MarActivityServiceImpl implements MarActivityService{

    private final MarActivityRepository marActivityRepository;
    @Autowired
    public MarActivityServiceImpl(MarActivityRepository marActivityRepository) {
        this.marActivityRepository = marActivityRepository;
    }

    @Override
    public MarActivity addActivity(MarActivity marActivity) {
        return marActivityRepository.save(marActivity);
    }

    @Override
    public List<MarActivity> fetchAllMarActivities() {
        return marActivityRepository.findAll();
    }
}
