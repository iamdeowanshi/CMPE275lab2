package com.cmpe275.lab2.repository;

import com.cmpe275.lab2.model.Player;
import com.cmpe275.lab2.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SponsorDAO extends JpaRepository<Sponsor,Long> {

}
