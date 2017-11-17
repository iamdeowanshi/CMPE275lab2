package com.cmpe275.lab2.repository;

import com.cmpe275.lab2.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorDAO extends JpaRepository<Sponsor,Long> {
}
