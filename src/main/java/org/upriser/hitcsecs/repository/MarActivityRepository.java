package org.upriser.hitcsecs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upriser.hitcsecs.entity.MarActivity;

@Repository
public interface MarActivityRepository extends JpaRepository<MarActivity,String> {
}
