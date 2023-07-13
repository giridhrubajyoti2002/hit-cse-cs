package org.upriser.hitcsecs.service;

import org.upriser.hitcsecs.entity.MarActivity;

import java.util.List;

public interface MarActivityService {
    MarActivity addActivity(MarActivity marActivity);

    List<MarActivity> fetchAllMarActivities();
}
