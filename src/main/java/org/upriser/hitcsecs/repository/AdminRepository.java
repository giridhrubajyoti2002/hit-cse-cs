package org.upriser.hitcsecs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upriser.hitcsecs.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
}
